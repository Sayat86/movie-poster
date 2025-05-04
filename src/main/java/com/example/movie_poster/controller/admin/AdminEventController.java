package com.example.movie_poster.controller.admin;

import com.example.movie_poster.event.EventService;
import com.example.movie_poster.event.dto.EventFullDto;
import com.example.movie_poster.event.dto.EventMapper;
import com.example.movie_poster.event.dto.EventShortDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventShortDto> findAll(@RequestParam String text,
                                       @RequestParam List<Integer> categories,
                                       @RequestParam boolean paid,
                                       @RequestParam String rangeStart,
                                       @RequestParam String rangeEnd,
                                       @RequestParam boolean onlyAvailable,
                                       @RequestParam String sort,
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
