package com.example.movie_poster.request;

import com.example.movie_poster.event.Event;
import com.example.movie_poster.event.EventRepository;
import com.example.movie_poster.event.EventState;
import com.example.movie_poster.exception.ConflictException;
import com.example.movie_poster.exception.ForbiddenException;
import com.example.movie_poster.exception.NotFoundException;
import com.example.movie_poster.user.User;
import com.example.movie_poster.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Request create(int userId, int eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));

        // 1. Проверка на повторный запрос
        boolean exists = requestRepository.existsByRequesterIdAndEventId(userId, eventId);
        if (exists) {
            throw new ConflictException("Запрос на участие уже был отправлен.");
        }

        // 2. Проверка, что пользователь не является инициатором события
        if (event.getInitiator().getId() == userId) {
            throw new ConflictException("Инициатор события не может отправить запрос на участие.");
        }

        // 3. Проверка на опубликованность события
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии.");
        }

        // 4. Проверка на лимит участников
        Integer confirmedRequests = requestRepository.countByEventIdAndStatus(eventId, RequestState.CONFIRMED);
        if (event.getParticipantLimit() > 0 && confirmedRequests >= event.getParticipantLimit()) {
            throw new ConflictException("Лимит участников исчерпан.");
        }

        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        request.setStatus(RequestState.PENDING);
        return requestRepository.save(request);
    }

    @Override
    public Request update(int userId, int requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос не найден"));

        if (request.getRequester().getId() != userId) {
            throw new ForbiddenException("Пользователь не может отменить чужой запрос");
        }

        if (request.getStatus() == RequestState.CANCELED) {
            return request; // уже отменен
        }

        request.setStatus(RequestState.CANCELED);
        return requestRepository.save(request);
    }

    @Override
    public List<Request> findAll(int userId) {
        return requestRepository.findAll();
    }
}
