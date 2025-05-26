package com.example.movie_poster.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CompilationUpdateDto {
    private Boolean pinned;
    @Size(min = 1, max = 50)
    private String title;
    private List<Integer> events;
}
