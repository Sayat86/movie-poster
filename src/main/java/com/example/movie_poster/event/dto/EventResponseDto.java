package com.example.movie_poster.event.dto;

import com.example.movie_poster.event.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDto {
    private int id; // ID события
    private String title; // Название события
    private String annotation; // Аннотация события
    private String description; // Описание события
    private LocalDateTime eventDate; // Дата события
    private Location location; // Местоположение события
    private Boolean paid; // Оплачено ли событие
    private Integer participantLimit; // Лимит участников
    private Boolean requestModeration; // Требуется ли модерация
    private String state; // Статус события (например, "PENDING")
}
