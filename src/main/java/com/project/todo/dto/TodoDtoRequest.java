package com.project.todo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDtoRequest {

    @NotNull(message = "The text field cannot be null.")
    @NotEmpty(message = "The text value cannot be empty.")
    private String text;

    private boolean done;

}
