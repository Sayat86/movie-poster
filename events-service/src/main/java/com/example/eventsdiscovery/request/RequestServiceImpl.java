package com.example.eventsdiscovery.request;

import com.example.eventsdiscovery.event.Event;
import com.example.eventsdiscovery.event.EventRepository;
import com.example.eventsdiscovery.event.EventState;
import com.example.eventsdiscovery.event.dto.EventRequestStatusUpdateRequest;
import com.example.eventsdiscovery.event.dto.EventRequestStatusUpdateResult;
import com.example.eventsdiscovery.exception.ConflictException;
import com.example.eventsdiscovery.exception.ForbiddenException;
import com.example.eventsdiscovery.exception.NotFoundException;
import com.example.eventsdiscovery.request.dto.ParticipationRequestDto;
import com.example.eventsdiscovery.request.dto.RequestMapper;
import com.example.eventsdiscovery.user.User;
import com.example.eventsdiscovery.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;

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

        //todo 4. Проверка на лимит участников
//        if (event.getParticipantLimit() > 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
//            throw new ConflictException("Лимит участников исчерпан.");
//        }
        if (event.getParticipantLimit() > 0 && event.getConfirmedRequests().equals(event.getParticipantLimit())) {
            throw new ConflictException("Лимит участников исчерпан.");
        }

        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            request.setStatus(RequestState.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        } else {
            request.setStatus(RequestState.PENDING);
        }
        eventRepository.save(event);
        return requestRepository.save(request);
    }

    @Override
    public Request update(int userId, int requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос не найден"));

        if (request.getRequester().getId() != userId) {
            throw new ForbiddenException("Пользователь не может отменить чужой запрос");
        }

        if (request.getStatus() == RequestState.CONFIRMED) {
            throw new ConflictException("Нельзя отменить уже подтверждённую заявку.");
        }

        if (request.getStatus() == RequestState.CANCELED) {
            return request; // уже отменен
        }

        request.setStatus(RequestState.CANCELED);
        return requestRepository.save(request);
    }

    @Override
    public List<Request> findByRequesterId(int userId) {
        return requestRepository.findByRequesterId(userId);
    }

    @Override
    public List<ParticipationRequestDto> findEventRequests(int userId, int eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с ID " + eventId + " не найдено"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Только инициатор события может просматривать запросы на участие");
        }

        List<Request> requests = requestRepository.findAllByEventId(eventId);
        return requestMapper.toResponse(requests);
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequests(int userId, int eventId, EventRequestStatusUpdateRequest updateRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("User is not the initiator of the event");
        }

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new ConflictException("No need to confirm requests for this event");
        }

        List<Request> requests = requestRepository.findAllById(updateRequest.getRequestIds());

        for (Request request : requests) {
            if (request.getStatus() != RequestState.PENDING) {
                throw new ConflictException("Request must have status PENDING");
            }
        }

        List<Request> confirmed = new ArrayList<>();
        List<Request> rejected = new ArrayList<>();

        int confirmedCount = requestRepository.countByEventIdAndStatus(eventId, RequestState.CONFIRMED);
        int availableSlots = event.getParticipantLimit() - confirmedCount;

        for (Request request : requests) {
            if (updateRequest.getStatus() == RequestState.CONFIRMED) {
                if (availableSlots > 0) {
                    request.setStatus(RequestState.CONFIRMED);
                    confirmed.add(request);
                    availableSlots--;
                    event.setConfirmedRequests(event.getConfirmedRequests()+1);
                } else {
                    throw new ConflictException("The participant limit has been reached");
                }
            } else if (updateRequest.getStatus() == RequestState.REJECTED) {
                request.setStatus(RequestState.REJECTED);
                rejected.add(request);
            }
        }

        requestRepository.saveAll(requests);

        if (updateRequest.getStatus() == RequestState.CONFIRMED && availableSlots == 0) {
            List<Request> pendingToReject = requestRepository
                    .findByEventIdAndStatus(eventId, RequestState.PENDING);
            for (Request pending : pendingToReject) {
                pending.setStatus(RequestState.REJECTED);
            }
            rejected.addAll(pendingToReject);
            requestRepository.saveAll(pendingToReject);
        }

        return new EventRequestStatusUpdateResult(
                requestMapper.toResponse(confirmed),
                requestMapper.toResponse(rejected)
        );
    }
}
