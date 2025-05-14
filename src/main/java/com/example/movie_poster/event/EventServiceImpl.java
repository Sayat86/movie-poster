package com.example.movie_poster.event;

import com.example.movie_poster.category.Category;
import com.example.movie_poster.category.CategoryRepository;
import com.example.movie_poster.exception.BadRequestException;
import com.example.movie_poster.exception.ConflictException;
import com.example.movie_poster.exception.NotFoundException;
import com.example.movie_poster.user.User;
import com.example.movie_poster.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Event> findEventAddedByUserId(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> pageResult = eventRepository.findByInitiatorId(userId, pageable);
        return pageResult.getContent();
    }

    @Override
    public Event create(Event event, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким ID не найден"));
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Категория с таким ID не найдена"));
        // Проверка на отрицательный лимит участников
        if (event.getParticipantLimit() != null && event.getParticipantLimit() < 0) {
            throw new BadRequestException("Лимит участников не может быть отрицательным");
        }
        event.setCategory(category);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        event.setViews(0);
        event.setConfirmedRequests(0);
        event.setIsPublished(false);
        return eventRepository.save(event);
    }

    @Override
    public Event findFullEventInfoByUser(int userId, int eventId) {
        return eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено или доступ запрещен"));
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
        if (existingEvent.getInitiator().getId() != userId) {
            throw new ConflictException("Редактировать событие может только его инициатор");
        }

        // - если дата указана, то она не должна быть раньше чем за 2 часа от текущего времени
        if (event.getEventDate() != null) {
            LocalDateTime nowPlus2Hours = LocalDateTime.now().plusHours(2);
            if (event.getEventDate().isBefore(nowPlus2Hours)) {
                throw new ConflictException("Дата и время события должны быть не ранее чем через 2 часа от текущего момента");
            }
            existingEvent.setEventDate(event.getEventDate());
        }

        // - если категория указана, проверяем есть ли в базе данных, если нет - исключение
        if (event.getCategory().getId() != null) {
            Category category = categoryRepository.findById(event.getCategory().getId())
                    .orElseThrow(() -> new NotFoundException("Категория с таким ID не найдена"));
            existingEvent.setCategory(category);
        }

        if (event.getState() != null) {
            EventState newState = event.getState();
            EventState currentState = existingEvent.getState();

            // Публикация уже опубликованного
            if (currentState == EventState.PUBLISHED && newState == EventState.PUBLISHED) {
                throw new ConflictException("Событие уже опубликовано");
            }

            // Публикация отмененного
            if (currentState == EventState.CANCELED && newState == EventState.PUBLISHED) {
                throw new ConflictException("Нельзя опубликовать отмененное событие");
            }

            // Отмена опубликованного
            if (currentState == EventState.PUBLISHED && newState == EventState.CANCELED) {
                throw new ConflictException("Нельзя отменить уже опубликованное событие");
            }

            // если всё ок, обновляем состояние
            existingEvent.setState(newState);
        }

        if (event.getAnnotation() != null) {
            existingEvent.setAnnotation(event.getAnnotation());
        }
        if (event.getDescription() != null) {
            existingEvent.setDescription(event.getDescription());
        }
        if (event.getLocation() != null) {
            existingEvent.setLocation(event.getLocation());
        }
        if (event.getPaid() != null) {
            existingEvent.setPaid(event.getPaid());
        }
        // Проверка на отрицательный лимит участников
        if (event.getParticipantLimit() != null && event.getParticipantLimit() < 0) {
            throw new BadRequestException("Лимит участников не может быть отрицательным");
        }
        if (event.getParticipantLimit() != null) {
            existingEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getTitle() != null) {
            existingEvent.setTitle(event.getTitle());
        }
        if (event.getRequestModeration() != null) {
            existingEvent.setRequestModeration(event.getRequestModeration());
        }

        return eventRepository.save(existingEvent);
    }

    @Override
    public Event updateByAdmin(int eventId, Event event) {
        Event existingEvent = findById(eventId);
        if (event.getEventDate() != null) {
            LocalDateTime nowPlus2Hours = LocalDateTime.now().plusHours(2);
            if (event.getEventDate().isBefore(nowPlus2Hours)) {
                throw new ConflictException("Дата и время события должны быть не ранее чем через 2 часа от текущего момента");
            }
            existingEvent.setEventDate(event.getEventDate());
        }

        // - если категория указана, проверяем есть ли в базе данных, если нет - исключение
        if (event.getCategory().getId() != null) {
            Category category = categoryRepository.findById(event.getCategory().getId())
                    .orElseThrow(() -> new NotFoundException("Категория с таким ID не найдена"));
            existingEvent.setCategory(category);
        }

        if (event.getState() != null) {
            EventState newState = event.getState();
            EventState currentState = existingEvent.getState();

            // Публикация уже опубликованного
            if (currentState == EventState.PUBLISHED && newState == EventState.PUBLISHED) {
                throw new ConflictException("Событие уже опубликовано");
            }

            // Публикация отмененного
            if (currentState == EventState.CANCELED && newState == EventState.PUBLISHED) {
                throw new ConflictException("Нельзя опубликовать отмененное событие");
            }

            // Отмена опубликованного
            if (currentState == EventState.PUBLISHED && newState == EventState.CANCELED) {
                throw new ConflictException("Нельзя отменить уже опубликованное событие");
            }

            // если всё ок, обновляем состояние
            existingEvent.setState(newState);
        }

        if (event.getAnnotation() != null) {
            existingEvent.setAnnotation(event.getAnnotation());
        }
        if (event.getDescription() != null) {
            existingEvent.setDescription(event.getDescription());
        }
        if (event.getLocation() != null) {
            existingEvent.setLocation(event.getLocation());
        }
        if (event.getPaid() != null) {
            existingEvent.setPaid(event.getPaid());
        }
        if (event.getParticipantLimit() != null) {
            existingEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getTitle() != null) {
            existingEvent.setTitle(event.getTitle());
        }
        if (event.getRequestModeration() != null) {
            existingEvent.setRequestModeration(event.getRequestModeration());
        }
        return eventRepository.save(existingEvent);
    }

    @Override
    public Event findById(int id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие не найдено или доступ запрещен"));
    }

    @Override
    public List<Event> findAll(String text, List<Integer> categories, boolean paid, LocalDateTime rangeStart,
                               LocalDateTime rangeEnd, boolean onlyAvailable, String sort, int from, int size) {
        Sort sorting = Sort.unsorted();
        if ("EVENT_DATE".equalsIgnoreCase(sort)) {
            sorting = Sort.by("eventDate").ascending();
        } else if ("VIEWS".equalsIgnoreCase(sort)) {
            sorting = Sort.by("views").descending();
        }

        Pageable pageable = PageRequest.of(from / size, size, sorting);

        Page<Event> eventsPage = eventRepository.findAllWithFilters(text, categories, paid, rangeStart, rangeEnd, pageable);

        List<Event> events = eventsPage.getContent();

        if (onlyAvailable) {
            events = events.stream()
                    .filter(event -> event.getParticipantLimit() == 0 ||
                            event.getConfirmedRequests() < event.getParticipantLimit())
                    .collect(Collectors.toList());
        }

        return events;
    }
}
