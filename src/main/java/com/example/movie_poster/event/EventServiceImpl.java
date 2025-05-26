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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
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
                throw new BadRequestException("Дата и время события должны быть не ранее чем через 2 часа от текущего момента");
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
            if (event.getAnnotation().isBlank()) {
                throw new BadRequestException("TODO");
            }
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
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с таким ID не найдена"));
        if (event.getEventDate() != null) {
            LocalDateTime nowPlus2Hours = LocalDateTime.now().plusHours(2);

            if (event.getEventDate().isBefore(nowPlus2Hours)) {
                throw new BadRequestException("Дата и время события должны быть не ранее чем через 2 часа от текущего момента");
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
    public Event findById(int id, String ipAddress) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));

        if (event.getState() != EventState.PUBLISHED) {
            throw new NotFoundException("Событие не найдено или ещё не опубликовано");
        }

//        if (!eventViewRepository.existsByEventAndIpAddress(event, ipAddress)) {
//            EventView view = new EventView();
//            view.setEvent(event);
//            view.setIpAddress(ipAddress);
//            view.setViewedAt(LocalDateTime.now());
//            eventViewRepository.save(view);

            event.setViews(event.getViews() + 1);
            eventRepository.save(event);
//        }

        return event;
    }

    @Override
    public List<Event> findAllPublic(String text, List<Integer> categories, Boolean paid,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                     Boolean onlyAvailable, String sort, int from, int size) {

        if (rangeStart != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Дата начала не может быть позже даты окончания");
        }

        Pageable pageable = PageRequest.of(from / size, size, getSort(sort));

        Specification<Event> spec = Specification.where((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("state"), EventState.PUBLISHED));

            if (text != null && !text.isBlank()) {
                String likeText = "%" + text.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("annotation")), likeText),
                        cb.like(cb.lower(root.get("description")), likeText)
                ));
            }

            if (categories != null && !categories.isEmpty()) {
                predicates.add(root.get("category").get("id").in(categories));
            }

            if (paid != null) {
                predicates.add(cb.equal(root.get("paid"), paid));
            }

            if (rangeStart != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("eventDate"), rangeStart));
            }

            if (rangeEnd != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("eventDate"), rangeEnd));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        List<Event> events = eventRepository.findAll(spec, pageable).getContent();

        if (onlyAvailable != null && onlyAvailable) {
            events = events.stream()
                    .filter(event -> event.getParticipantLimit() == 0 ||
                            event.getConfirmedRequests() < event.getParticipantLimit())
                    .collect(Collectors.toList());
        }

        return events;
    }

    private Sort getSort(String sort) {
        if ("EVENT_DATE".equalsIgnoreCase(sort)) {
            return Sort.by("eventDate").ascending();
        } else if ("VIEWS".equalsIgnoreCase(sort)) {
            return Sort.by("views").descending();
        }
        return Sort.unsorted();
    }

    @Override
    public List<Event> findAllAdmin(Integer users, List<EventState> states, List<Integer> categories,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);

        Specification<Event> spec = Specification.where(null);

        if (users != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("initiator").get("id"), users));
        }

        if (states != null && !states.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    root.get("state").in(states));
        }

        if (categories != null && !categories.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    root.get("category").get("id").in(categories));
        }

        if (rangeStart != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("eventDate"), rangeStart));
        }

        if (rangeEnd != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("eventDate"), rangeEnd));
        }

        return eventRepository.findAll(spec, pageable).getContent();
    }
}
