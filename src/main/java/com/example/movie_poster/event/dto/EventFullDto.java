package com.example.movie_poster.event.dto;

import com.example.movie_poster.category.dto.CategoryCreateDto;
import com.example.movie_poster.event.Location;
import com.example.movie_poster.user.dto.UserCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {

    private Long id;
    private String title;
    private String annotation;
    private String description;
    private String eventDate;
    private String createdOn;
    private String publishedOn;

    private CategoryCreateDto category;
    private UserCreateDto initiator;
    private Location location;

    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;

    private String state;

    private Integer confirmedRequests;
    private Integer views;
}
