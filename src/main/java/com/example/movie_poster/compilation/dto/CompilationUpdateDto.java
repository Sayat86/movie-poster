package com.example.movie_poster.compilation.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompilationUpdateDto {
    private Boolean pinned;
    private String title;
    private List<Integer> events;
}
