package com.project.todo.service;

import com.project.todo.entity.Todo;
import com.project.todo.exception.InvalidRequestBodyException;
import com.project.todo.exception.ResourceNotFoundException;
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
        return todoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(String id, Todo todo) {
        if (todo.getText() == null && todo.getDone() == null) {
            throw new InvalidRequestBodyException("The request body is empty.");
        }

        Todo targetUpdateTodo = getTodoById(id);

        if (todo.getText() != null) {
            targetUpdateTodo.setText(todo.getText());
        }

        targetUpdateTodo.setDone(todo.getDone());

        return todoRepository.save(targetUpdateTodo);
    }

    public void deleteTodoById(String id) {
        Todo targetDeleteTodo = getTodoById(id);
        todoRepository.delete(targetDeleteTodo);
    }
}
