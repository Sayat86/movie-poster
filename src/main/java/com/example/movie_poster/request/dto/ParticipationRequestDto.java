package com.example.movie_poster.request.dto;

import com.example.movie_poster.request.RequestState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private Integer requester;
    private LocalDateTime created;
    private Integer id;
    private Integer event;
    private RequestState status;
}
