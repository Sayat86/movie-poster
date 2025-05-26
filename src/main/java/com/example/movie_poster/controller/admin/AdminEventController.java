package com.example.movie_poster.controller.admin;

import com.example.movie_poster.event.Event;
import com.example.movie_poster.event.EventService;
import com.example.movie_poster.event.EventState;
import com.example.movie_poster.event.dto.EventFullDto;
import com.example.movie_poster.event.dto.EventMapper;
import com.example.movie_poster.event.dto.EventShortDto;
import com.example.movie_poster.event.dto.UpdateEventAdminRequest;
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
    public List<EventFullDto> findAllAdmin(@RequestParam(required = false) Integer users,
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
