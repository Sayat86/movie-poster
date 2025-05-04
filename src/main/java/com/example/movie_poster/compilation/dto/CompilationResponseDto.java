package com.example.movie_poster.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CompilationResponseDto {
    private Integer id;
    private Boolean pinned;
    @NotBlank(message = "Заголовок не может быть пустым")
    private String title;
    private List<Integer> events;
}
