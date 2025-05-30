package com.example.eventsdiscovery.controller.personal;

import com.example.eventsdiscovery.event.Event;
import com.example.eventsdiscovery.event.EventService;
import com.example.eventsdiscovery.event.dto.*;
import com.example.eventsdiscovery.request.RequestService;
import com.example.eventsdiscovery.request.dto.ParticipationRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.eventsdiscovery.utils.RequestConstants.DEFAULT_FROM;
import static com.example.eventsdiscovery.utils.RequestConstants.DEFAULT_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final RequestService requestService;

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
    @ResponseStatus(HttpStatus.CREATED)
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
    public List<ParticipationRequestDto> findParticipationRequestsForUserEvents(
            @PathVariable int userId,
            @PathVariable int eventId
    ) {
        return requestService.findEventRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequests(
            @PathVariable int userId,
            @PathVariable int eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest updateRequest
    ) {
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = requestService.updateEventRequests(userId, eventId, updateRequest);
        return eventRequestStatusUpdateResult;
    }
}
