package com.example.eventsdiscovery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException e) {
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTrace : e.getStackTrace()) {
            errors.add(stackTrace.toString());
        }

        return new ErrorResponse(
                errors,
                e.getMessage(),
                "Объект не найден",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(final ConflictException e) {
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTrace : e.getStackTrace()) {
            errors.add(stackTrace.toString());
        }
        return new ErrorResponse(
                errors,
                e.getMessage(),
                "Запрос уже существует",
                HttpStatus.CONFLICT,
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden(final ForbiddenException e) {
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTrace : e.getStackTrace()) {
            errors.add(stackTrace.toString());
        }
        return new ErrorResponse(
                errors,
                e.getMessage(),
                "Запрещено",
                HttpStatus.FORBIDDEN,
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(final BadRequestException e) {
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTrace : e.getStackTrace()) {
            errors.add(stackTrace.toString());
        }
        return new ErrorResponse(
                errors,
                e.getMessage(),
                "Неверный запрос",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now());
    }
}
