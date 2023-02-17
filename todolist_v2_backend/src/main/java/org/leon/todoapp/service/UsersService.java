package org.leon.todoapp.service;

import org.apache.catalina.User;
import org.leon.todoapp.entity.Users;
import org.leon.todoapp.exceptions.AttributeException;
import org.leon.todoapp.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {

    public Users create( Users user) throws AttributeException;
    public List<Users> getAll();
}
