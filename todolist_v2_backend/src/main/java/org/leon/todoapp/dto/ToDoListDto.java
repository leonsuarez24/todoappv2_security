package org.leon.todoapp.dto;

import java.io.Serializable;

public record ToDoListDto(
        String name,
        String content) implements Serializable {
}
