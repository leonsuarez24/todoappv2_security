package org.leon.todoapp.repository;

import org.leon.todoapp.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    boolean existsByName(String name);
    Optional<ToDoList> findByName( String name);
}
