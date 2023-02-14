package org.leon.todoapp.dto;

import org.springframework.http.HttpStatus;

public record MessageDto(
        HttpStatus status,
        String message) {
}
