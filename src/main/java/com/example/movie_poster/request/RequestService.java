package com.example.movie_poster.request;

import com.example.movie_poster.event.dto.EventRequestStatusUpdateRequest;
import com.example.movie_poster.event.dto.EventRequestStatusUpdateResult;
import com.example.movie_poster.request.dto.ParticipationRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface RequestService {
    Request create(int userId, int eventId);
    Request update(int userId, int requestId);
    List<Request> findByRequesterId(int userId);
    List<ParticipationRequestDto> findEventRequests(int userId, int eventId);

    EventRequestStatusUpdateResult updateEventRequests(int userId, int eventId, EventRequestStatusUpdateRequest updateRequest);
}
