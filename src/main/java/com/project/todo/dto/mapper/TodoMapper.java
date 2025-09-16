package com.project.todo.dto.mapper;

import com.project.todo.dto.TodoDto;
import com.project.todo.dto.TodoDtoRequest;
import com.project.todo.entity.Todo;
import org.springframework.beans.BeanUtils;

public class TodoMapper {

    public static Todo toEntity(TodoDto todoDto) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoDto, todo);
        return todo;
    }

    public static Todo toEntity(TodoDtoRequest todoDtoRequest) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoDtoRequest, todo);
        return todo;
    }

    public static TodoDto toDto(Todo todo) {
        TodoDto todoDto = new TodoDto();
        BeanUtils.copyProperties(todo, todoDto);
        return todoDto;
    }

}
