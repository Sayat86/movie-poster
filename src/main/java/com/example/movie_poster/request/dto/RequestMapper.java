package com.example.movie_poster.request.dto;

import com.example.movie_poster.request.Request;
import org.springframework.stereotype.Component;

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
}
