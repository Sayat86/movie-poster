package com.example.movie_poster.controller.personal;

import com.example.movie_poster.event.Event;
import com.example.movie_poster.event.EventService;
import com.example.movie_poster.event.dto.*;
import com.example.movie_poster.request.dto.ParticipationRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.movie_poster.utils.RequestConstants.DEFAULT_FROM;
import static com.example.movie_poster.utils.RequestConstants.DEFAULT_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> findEventAddByUserId(@PathVariable int userId,
                                                   @RequestParam(defaultValue = DEFAULT_FROM) int from,
                                                   @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        int page = from / size;
        return eventService.findEventAddedByUserId(userId, page, size)
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{userId}/events")
    public EventFullDto create(@PathVariable int userId,
                               @Valid @RequestBody EventCreateDto eventCreate) {
        Event event = eventMapper.fromCreate(eventCreate);
        return eventMapper.toFullDto(eventService.create(event, userId));
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto findFullEventInfoByUser(@PathVariable int userId,
                                                @PathVariable int eventId) {
        Event event = eventService.findFullEventInfoByUser(userId, eventId);
        return eventMapper.toFullDto(event);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto update(@PathVariable int userId,
                               @PathVariable int eventId,
                               @Valid @RequestBody UpdateEventUserRequest updateEvent) {
        Event event = eventMapper.fromUserUpdate(updateEvent);
        return eventMapper.toFullDto(eventService.updateByUser(event, userId, eventId));
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> findParticipationRequestsForUserEvents(@PathVariable int userId,
                                                                                @PathVariable int eventId) {
        return null;
    }
}
