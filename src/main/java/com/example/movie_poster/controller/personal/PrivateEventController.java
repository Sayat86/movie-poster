package com.example.movie_poster.controller.personal;

import com.example.movie_poster.event.EventRepository;
import com.example.movie_poster.event.dto.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PrivateEventController {
    private final EventRepository eventRepository;
   // private final EventMapper eventMapper;
}
