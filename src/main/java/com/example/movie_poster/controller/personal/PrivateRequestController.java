package com.example.movie_poster.controller.personal;

import com.example.movie_poster.request.RequestService;
import com.example.movie_poster.request.dto.ParticipationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateRequestController {
    private final RequestService requestService;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> findAll(@PathVariable int userId) {
        return null;
    }

    @PostMapping("/{userId}/requests/{eventId}")
    public ParticipationRequestDto create(@PathVariable int userId,
                                          @PathVariable int eventId) {
        return null;
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto update(@PathVariable int userId,
                                          @PathVariable int requestId) {
        return null;
    }
}
