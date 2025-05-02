package com.example.movie_poster.event;

import com.example.movie_poster.category.Category;
import com.example.movie_poster.category.CategoryRepository;
import com.example.movie_poster.event.dto.EventCreateDto;
import com.example.movie_poster.exception.BadRequestException;
import com.example.movie_poster.exception.ConflictException;
import com.example.movie_poster.exception.NotFoundException;
import com.example.movie_poster.user.User;
import com.example.movie_poster.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Event create(Event event, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким ID не найден"));
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Категория с таким ID не найдена"));
        event.setCategory(category);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        event.setViews(0);
        event.setConfirmedRequests(0);
        return eventRepository.save(event);
    }

    @Override
    public Event updateByUser(Event event, int userId, int eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким ID не найден"));
        Event existingEvent = eventRepository.findById(eventId)
                        .orElseThrow(() -> new NotFoundException("Событие с таким ID не найдено"));
        // Проверка
        // - опубликованное событие нельзя редактировать
        if (existingEvent.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Опубликованное событие нельзя редактировать");
        }
        // - редактировать может только владелец события
        // - если дата указана, то она не должна быть раньше чем за 2 часа от текущего времени
        // - если категория указана, проверяем есть ли в базе данных, если нет - исключение

        if (event.getAnnotation() != null) {
            existingEvent.setAnnotation(event.getAnnotation());
        }
        return null;
    }

    @Override
    public List<Event> findEventAddedByUserId(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> pageResult = eventRepository.findByInitiatorId(userId, pageable);
        return pageResult.getContent();
    }
}
