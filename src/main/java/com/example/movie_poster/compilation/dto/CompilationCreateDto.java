package com.example.movie_poster.compilation.dto;

import com.example.movie_poster.event.dto.EventShortDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CompilationCreateDto {
    private Boolean pinned;
    @NotBlank(message = "Заголовок не может быть пустым")
    private String title;
    private List<Integer> events;
}

// {
//      "events": [1, 5, 6]
// }

// {
//      "events": [
//          {
//              "id": 1,
//          },
//          {}
//      ]
// }
