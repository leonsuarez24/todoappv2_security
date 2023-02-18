package org.leon.todoapp.controller;

import jakarta.validation.Valid;
import org.leon.todoapp.dto.MessageDto;
import org.leon.todoapp.entity.Authorities;
import org.leon.todoapp.entity.Users;
import org.leon.todoapp.exceptions.AttributeException;
import org.leon.todoapp.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<MessageDto> create (@Valid @RequestBody Users users) throws AttributeException {
        Authorities authorities = new Authorities(1L, "ROLE_USER");
        Set<Authorities> authoritiesSet = new HashSet<>();
        authoritiesSet.add(authorities);
        users.setAuthorities(authoritiesSet);
        Users users1 = usersService.create(users);
        return new ResponseEntity<>(new MessageDto(HttpStatus.CREATED, "user has been created") , HttpStatus.CREATED);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<MessageDto> createAdmin (@Valid @RequestBody Users users) throws AttributeException {
        Authorities authorities = new Authorities(2L, "ROLE_ADMIN");
        Set<Authorities> authoritiesSet = new HashSet<>();
        authoritiesSet.add(authorities);
        users.setAuthorities(authoritiesSet);
        Users users1 = usersService.create(users);
        return new ResponseEntity<>(new MessageDto(HttpStatus.CREATED, "admin has been created") , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll ()  {
        return new ResponseEntity<>(usersService.getAll() , HttpStatus.OK);
    }
}
