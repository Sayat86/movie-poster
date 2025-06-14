package com.example.eventsdiscovery.controller.admin;

import com.example.eventsdiscovery.event.Event;
import com.example.eventsdiscovery.event.EventService;
import com.example.eventsdiscovery.event.EventState;
import com.example.eventsdiscovery.event.dto.EventFullDto;
import com.example.eventsdiscovery.event.dto.EventMapper;
import com.example.eventsdiscovery.event.dto.UpdateEventAdminRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventFullDto> findAllAdmin(@RequestParam(required = false) List<Integer> users,
                                       @RequestParam(required = false) List<EventState> states,
                                       @RequestParam(required = false) List<Integer> categories,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size) {
        int page = from / size;
        return eventMapper.toFullDto(eventService.findAllAdmin(users, states, categories, rangeStart,
                rangeEnd, page, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable int eventId,
                               @Valid @RequestBody UpdateEventAdminRequest updateEvent) {
        Event event = eventMapper.fromAdminUpdate(updateEvent);
        return eventMapper.toFullDto(eventService.updateByAdmin(eventId, event));
    }
}
