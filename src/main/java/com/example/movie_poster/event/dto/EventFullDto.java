package com.example.movie_poster.event.dto;

import com.example.movie_poster.category.dto.CategoryCreateDto;
import com.example.movie_poster.category.dto.CategoryResponseDto;
import com.example.movie_poster.event.EventState;
import com.example.movie_poster.event.Location;
import com.example.movie_poster.user.dto.UserCreateDto;
import com.example.movie_poster.user.dto.UserResponseDto;
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
public class EventFullDto {

    private int id;
    private String title;
    private String annotation;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    private CategoryResponseDto category;
    private UserResponseDto initiator;
    private Location location;

    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;

    private EventState state;

    private Integer confirmedRequests;
    private Integer views;
}
