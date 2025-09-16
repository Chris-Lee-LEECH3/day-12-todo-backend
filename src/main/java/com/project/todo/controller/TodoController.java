package com.project.todo.controller;

import com.project.todo.entity.Todo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @GetMapping
    public List<Todo> getAllTodos() {
        return List.of();
    }

}
