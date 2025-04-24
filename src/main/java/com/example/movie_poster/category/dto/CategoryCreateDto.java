package com.example.movie_poster.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryCreateDto {
    @NotBlank(message = "Имя пользователя не может быть пустой")
    private String name;
}
