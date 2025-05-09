package com.example.movie_poster.request;

import com.example.movie_poster.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    Request create(int userId, int eventId);
    Request update(int userId, int requestId);
    List<Request> findAll(int userId);
    List<ParticipationRequestDto> findEventRequests(int userId, int eventId);
}
