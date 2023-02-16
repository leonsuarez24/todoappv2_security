package org.leon.todoapp.dto;

public record LoginRequest(
        String username,
        String password
) {
}
