package com.example.movie_poster.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDto {
    @NotBlank(message = "Имя пользователя не может быть пустой")
    @Size(min = 2, max = 250)
    private String name;

    @NotBlank(message = "Почта пользователя не может быть пустой")
    @Email(message = "Почта должна быть в формате \"user@mail.com\"")
    @Size(min = 6, max = 254)
    private String email;
}
