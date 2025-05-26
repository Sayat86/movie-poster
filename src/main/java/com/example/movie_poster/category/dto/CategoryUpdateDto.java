package com.example.movie_poster.category.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryUpdateDto {
    @Size(min = 1, max = 50)
    private String name;
}
