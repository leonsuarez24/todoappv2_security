package org.leon.todoapp.controller;

import jakarta.validation.Valid;
import org.leon.todoapp.dto.MessageDto;
import org.leon.todoapp.entity.Users;
import org.leon.todoapp.exceptions.AttributeException;
import org.leon.todoapp.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/create")
    public ResponseEntity<MessageDto> create (@Valid @RequestBody Users users) throws AttributeException {
        Users users1 = usersService.create(users);
        return new ResponseEntity<>(new MessageDto(HttpStatus.CREATED, "user has been created") , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll ()  {
        return new ResponseEntity<>(usersService.getAll() , HttpStatus.OK);
    }
}
