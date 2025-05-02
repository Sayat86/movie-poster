package com.example.movie_poster.event.dto;

import com.example.movie_poster.event.EventStateActionUser;
import com.example.movie_poster.event.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {
    private String title;
    private String annotation;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Integer category;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private Location location;
    private EventStateActionUser stateAction;
}
