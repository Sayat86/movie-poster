package com.example.movie_poster.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private final List<String> errors;
    private final String message;
    private final String reason;
    private final HttpStatus status;
    private final LocalDateTime timestamp;
}
