package com.project.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDtoRequest {

    @NotBlank(message = "The text field is required.")
    private String text;

    private boolean done;

}
