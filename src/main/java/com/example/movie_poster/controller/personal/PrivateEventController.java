package com.example.movie_poster.controller.personal;

import com.example.movie_poster.event.Event;
import com.example.movie_poster.event.EventRepository;
import com.example.movie_poster.event.EventService;
import com.example.movie_poster.event.dto.EventCreateDto;
import com.example.movie_poster.event.dto.EventFullDto;
import com.example.movie_poster.event.dto.EventMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    @PostMapping("/{userId}/events")
    public EventFullDto create(@PathVariable int userId,
                               @Valid @RequestBody EventCreateDto eventCreate) {
        Event event = eventMapper.fromCreate(eventCreate);
        return eventMapper.toFullDto(eventService.create(event, userId));
    }
}
