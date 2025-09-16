package com.project.todo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Todo {
    @Id
    private String id;
    private String text;
    private boolean done;

    @PrePersist
    public void ensureId(){
        this.id = UUID.randomUUID().toString();
    }

}
