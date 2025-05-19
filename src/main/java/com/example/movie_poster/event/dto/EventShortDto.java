package com.example.movie_poster.event.dto;

import com.example.movie_poster.category.dto.CategoryResponseDto;
import com.example.movie_poster.user.dto.UserResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private CategoryResponseDto category;
    private UserResponseDto initiator;

    private Boolean paid;
    private Integer confirmedRequests;
    private Integer views;
}
