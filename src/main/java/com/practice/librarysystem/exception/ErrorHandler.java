package com.practice.librarysystem.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handle(DataConflictException e) {
        log.warn(e.getMessage());

        return ApiError.builder()
                .status(HttpStatus.CONFLICT.name())
                .reason("Data conflict")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handle(SQLException e) {
        log.warn(e.getMessage());

        return ApiError.builder()
                .status(HttpStatus.CONFLICT.name())
                .reason("Data conflict")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(ValidationException e) {
        log.warn(e.getMessage());

        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handle(NotFoundException e) {
        log.warn(e.getMessage());

        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason("Object not found")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
