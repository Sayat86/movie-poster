package com.example.movie_poster.controller.everyone;

import com.example.movie_poster.event.EventService;
import com.example.movie_poster.event.dto.EventFullDto;
import com.example.movie_poster.event.dto.EventMapper;
import com.example.movie_poster.event.dto.EventShortDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventShortDto> findAll(@RequestParam(required = false) String text,
                                       @RequestParam(required = false) List<Integer> categories,
                                       @RequestParam(required = false) boolean paid,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                       @RequestParam(required = false) boolean onlyAvailable,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size) {
        int page = from / size;
        return eventMapper.toShortDto(eventService.findAll(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, page, size));
    }

    @GetMapping("/{id}")
    public EventFullDto findById(@PathVariable int id) {
        return eventMapper.toFullDto(eventService.findById(id));
    }
}
