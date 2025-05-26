package com.example.movie_poster.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateDto {
    @NotBlank(message = "Имя пользователя не может быть пустой")
    @Size(min = 1, max = 50)
    private String name;
}
