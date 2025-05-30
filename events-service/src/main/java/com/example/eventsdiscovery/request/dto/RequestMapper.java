package com.example.eventsdiscovery.request.dto;

import com.example.eventsdiscovery.request.Request;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestMapper {
    public ParticipationRequestDto toResponse(Request request) {
        ParticipationRequestDto dto = new ParticipationRequestDto();
        dto.setId(request.getId());
        dto.setCreated(request.getCreated());
        dto.setStatus(request.getStatus());
        dto.setRequester(request.getRequester().getId());
        dto.setEvent(request.getEvent().getId());
        return dto;
    }


    public List<ParticipationRequestDto> toResponse(List<Request> requests) {
        return requests.stream()
                .map(this::toResponse)
                .toList();
    }
}
