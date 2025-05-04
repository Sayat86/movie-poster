package com.example.movie_poster.event.dto;

import com.example.movie_poster.category.dto.CategoryCreateDto;
import com.example.movie_poster.category.dto.CategoryResponseDto;
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
public class EventShortDto {

    private Integer id;
    private String title;
    private String annotation;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private CategoryResponseDto category;
    private UserResponseDto initiator;

    private Boolean paid;
    private Integer confirmedRequests;
    private Integer views;
}
