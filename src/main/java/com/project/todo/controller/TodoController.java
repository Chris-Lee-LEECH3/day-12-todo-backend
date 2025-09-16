package com.project.todo.controller;

import com.project.todo.dto.TodoDto;
import com.project.todo.dto.TodoDtoRequest;
import com.project.todo.dto.mapper.TodoMapper;
import com.project.todo.entity.Todo;
import com.project.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo createTodo(@Valid @RequestBody TodoDtoRequest todoDtorequest) {
        Todo todo = TodoMapper.toEntity(todoDtorequest);
        return todoService.createTodo(todo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Todo updateTodo(@Valid @RequestBody TodoDtoRequest todoDtorequest, @PathVariable String id) {
        Todo todo = TodoMapper.toEntity(todoDtorequest);
        return todoService.updateTodo(id, todo);
    }

}
