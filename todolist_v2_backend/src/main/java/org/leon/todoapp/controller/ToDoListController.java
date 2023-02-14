package org.leon.todoapp.controller;

import jakarta.validation.Valid;
import org.leon.todoapp.dto.MessageDto;
import org.leon.todoapp.dto.ToDoListDto;
import org.leon.todoapp.entity.ToDoList;
import org.leon.todoapp.exceptions.AttributeException;
import org.leon.todoapp.exceptions.ResourceNotFoundException;
import org.leon.todoapp.service.ToDoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todolist")
@CrossOrigin
public class ToDoListController {

    private final ToDoListService toDoListService;

    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @GetMapping
    public ResponseEntity<List<ToDoList>> getAll(){
        return new ResponseEntity<>(toDoListService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoList> getOne(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(toDoListService.getOne(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageDto> save(@Valid @RequestBody ToDoList toDoList) throws AttributeException {
        ToDoList toDoListSaved = toDoListService.save(toDoList);
        String message = "task " + toDoList.getName() + " has been saved";
        return new ResponseEntity<>(new MessageDto(HttpStatus.CREATED, message), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable(name = "id") Long id, @Valid @RequestBody ToDoListDto toDoListDto) throws ResourceNotFoundException, AttributeException {
        ToDoList toDoListUpdated = toDoListService.update(id, toDoListDto);
        String message = "task " + toDoListDto.name() + " has been updated";
        return new ResponseEntity<>(new MessageDto(HttpStatus.CREATED, message), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        ToDoList toDoListDeleted = toDoListService.delete(id);
        String message = "task " + toDoListDeleted.getName() + " has been deleted";
        return new ResponseEntity<>(new MessageDto(HttpStatus.CREATED, message), HttpStatus.OK);
    }
}
