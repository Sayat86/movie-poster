package com.example.movie_poster.controller.personal;

import com.example.movie_poster.request.RequestService;
import com.example.movie_poster.request.dto.ParticipationRequestDto;
import com.example.movie_poster.request.dto.RequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateRequestController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> findAll(@PathVariable int userId) {
        return requestMapper.toResponse(requestService.findAll(userId));
    }

    @PostMapping("/{userId}/requests/{eventId}")
    public ParticipationRequestDto create(@PathVariable int userId,
                                          @PathVariable int eventId) {
        return requestMapper.toResponse(requestService.create(userId, eventId));
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto update(@PathVariable int userId,
                                          @PathVariable int requestId) {
        return requestMapper.toResponse(requestService.update(userId, requestId));
    }
}
