package com.project.todo.service;

import com.project.todo.dto.TodoDto;
import com.project.todo.dto.mapper.TodoMapper;
import com.project.todo.entity.Todo;
import com.project.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(String id) {
        return todoRepository.findById(id).orElse(null);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(String id, Todo todo) {
        Todo targetUpdateTodo = getTodoById(id);
        if (targetUpdateTodo == null) {
            return null;
        }

        if (todo.getText() != null) {
            targetUpdateTodo.setText(todo.getText());
        }

        targetUpdateTodo.setDone(todo.isDone());

        return todoRepository.save(targetUpdateTodo);
    }
}
