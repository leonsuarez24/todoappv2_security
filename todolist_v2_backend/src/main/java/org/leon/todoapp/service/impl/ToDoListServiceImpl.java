package org.leon.todoapp.service.impl;

import org.leon.todoapp.dto.ToDoListDto;
import org.leon.todoapp.entity.ToDoList;
import org.leon.todoapp.exceptions.AttributeException;
import org.leon.todoapp.exceptions.ResourceNotFoundException;
import org.leon.todoapp.repository.ToDoListRepository;
import org.leon.todoapp.service.ToDoListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    private final ToDoListRepository toDoListRepository;

    public ToDoListServiceImpl(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }

    @Override
    public List<ToDoList> getAll() {
        return toDoListRepository.findAll();
    }

    @Override
    public ToDoList getOne(Long id) throws ResourceNotFoundException {
        return toDoListRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found")
        );
    }

    @Override
    public ToDoList save(ToDoList toDoList) throws AttributeException {
        /*if (toDoListRepository.existsByName(toDoList.getName())){
            throw new AttributeException("The task name " + toDoList.getName() + " is already in the list");
        }*/
        return toDoListRepository.save(toDoList);
    }

    @Override
    public ToDoList update(Long id, ToDoListDto toDoListDto) throws ResourceNotFoundException, AttributeException {
        ToDoList toDoList = toDoListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));

        /* if (toDoListRepository.existsByName(toDoListDto.name()) && !toDoListRepository.findByName(toDoListDto.name()).get().getId().equals(id)){
            throw new AttributeException("The task name " + toDoListDto.name() + " is already in the list");
         } */

        toDoList.setName(toDoListDto.name());
        toDoList.setContent(toDoListDto.content());
        return toDoListRepository.save(toDoList);
    }

    @Override
    public ToDoList delete(Long id) throws ResourceNotFoundException {
        ToDoList toDoList = toDoListRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found"));
        toDoListRepository.delete(toDoList);
        return toDoList;
    }

    @Override
    public List<ToDoList> findAllByUsersId(Long usersId) {
        return toDoListRepository.findByUsersId(usersId);
    }
}
