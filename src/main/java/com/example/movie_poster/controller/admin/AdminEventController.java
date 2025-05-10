package com.example.movie_poster.controller.admin;

import com.example.movie_poster.event.Event;
import com.example.movie_poster.event.EventService;
import com.example.movie_poster.event.dto.EventFullDto;
import com.example.movie_poster.event.dto.EventMapper;
import com.example.movie_poster.event.dto.EventShortDto;
import com.example.movie_poster.event.dto.UpdateEventAdminRequest;
import lombok.RequiredArgsConstructor;
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
    public List<EventShortDto> findAll(@RequestParam(required = false) String text,
                                       @RequestParam(required = false) List<Integer> categories,
                                       @RequestParam(required = false) boolean paid,
                                       @RequestParam(required = false) LocalDateTime rangeStart,
                                       @RequestParam(required = false) LocalDateTime rangeEnd,
                                       @RequestParam(required = false) boolean onlyAvailable,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size) {
        int page = from / size;
        return eventMapper.toShortDto(eventService.findAll(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, page, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable int eventId,
                               @RequestBody UpdateEventAdminRequest updateEvent) {
        Event event = eventMapper.fromAdminUpdate(updateEvent);
        return eventMapper.toFullDto(eventService.updateByAdmin(eventId, event));
    }
}
