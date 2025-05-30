package com.example.eventsdiscovery.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateDto {
    private String name;

    @Email(message = "Почта должна быть в формате \"user@mail.com\"")
    private String email;
}
