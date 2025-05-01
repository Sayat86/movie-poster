package com.example.movie_poster.compilation.dto;

import com.example.movie_poster.event.dto.EventShortDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompilationUpdateDto {
    private Boolean pinned;
    private String title;
    private EventShortDto eventShortDto;
}
