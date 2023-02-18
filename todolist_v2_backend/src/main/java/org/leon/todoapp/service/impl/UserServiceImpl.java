package org.leon.todoapp.service.impl;

import org.leon.todoapp.entity.Authorities;
import org.leon.todoapp.entity.Users;
import org.leon.todoapp.exceptions.AttributeException;
import org.leon.todoapp.repository.UsersRepository;
import org.leon.todoapp.service.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users create(Users user) throws AttributeException {
        if (usersRepository.existsByUsername(user.getUsername())){
            throw new AttributeException("username already in use");
        }
        if (usersRepository.existsByEmail(user.getEmail())){
            throw new AttributeException("email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    @Override
    public List<Users> getAll() {
        return usersRepository.findAll();
    }
}
