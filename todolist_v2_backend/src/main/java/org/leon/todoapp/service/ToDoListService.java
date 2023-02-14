package org.leon.todoapp.service;

import org.leon.todoapp.dto.ToDoListDto;
import org.leon.todoapp.entity.ToDoList;
import org.leon.todoapp.exceptions.AttributeException;
import org.leon.todoapp.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ToDoListService {

    List<ToDoList> getAll();
    ToDoList getOne(Long id) throws ResourceNotFoundException;
    ToDoList save(ToDoList toDoList) throws AttributeException;
    ToDoList update(Long id, ToDoListDto toDoListDto) throws ResourceNotFoundException, AttributeException;
    ToDoList delete(Long id) throws ResourceNotFoundException;
}
