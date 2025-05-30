package com.example.movie_poster.compilation.dto;

import com.example.movie_poster.event.dto.EventShortDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CompilationCreateDto {
    private boolean pinned;
    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 1, max = 50)
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
