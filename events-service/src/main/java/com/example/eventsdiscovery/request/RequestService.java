package com.example.eventsdiscovery.request;

import com.example.eventsdiscovery.event.dto.EventRequestStatusUpdateRequest;
import com.example.eventsdiscovery.event.dto.EventRequestStatusUpdateResult;
import com.example.eventsdiscovery.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    Request create(int userId, int eventId);
    Request update(int userId, int requestId);
    List<Request> findByRequesterId(int userId);
    List<ParticipationRequestDto> findEventRequests(int userId, int eventId);

    EventRequestStatusUpdateResult updateEventRequests(int userId, int eventId, EventRequestStatusUpdateRequest updateRequest);
}
