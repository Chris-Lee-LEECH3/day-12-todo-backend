package com.project.todo;

import com.google.gson.Gson;
import com.project.todo.dto.TodoDto;
import com.project.todo.entity.Todo;
import com.project.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.not;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    public void setup() {
        todoRepository.deleteAll();
    }

    @Test
    void should_response_empty_list_when_index_with_no_any_todo() throws Exception {
        MockHttpServletRequestBuilder request = get("/todos")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_response_one_todo_when_index_with_one_todo() throws Exception {
        Todo todo = new Todo(null, "Buy milk", false);
        todoRepository.save(todo);

        MockHttpServletRequestBuilder request = get("/todos")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].text").value("Buy milk"))
                .andExpect(jsonPath("$[0].done").value(false));
    }

    @Test
    void should_response_a_new_todo_when_create() throws Exception {
        Gson gson = new Gson();
        TodoDto todoDto = new TodoDto("Buy milk", false);
        String requestBody = gson.toJson(todoDto);

        MockHttpServletRequestBuilder request = post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text").value("Buy milk"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_response_status_422_when_create_with_empty_text() throws Exception {
        Gson gson = new Gson();
        TodoDto todoDto = new TodoDto("", false);
        String requestBody = gson.toJson(todoDto);

        MockHttpServletRequestBuilder request = post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_response_status_422_when_create_with_missing_text_field() throws Exception {
        String requestBody = """
                {
                    "done": false
                }
                """;

        MockHttpServletRequestBuilder request = post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_response_status_201_and_ignore_client_sent_id_when_request_body_has_id_() throws Exception {
        String requestBody = """
        {
         "id": "client-sent", "text": "Buy bread", "done": false
        }
       """;

        MockHttpServletRequestBuilder request = post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(not("client-sent")));
    }

    @Test
    void should_return_updated_todo_when_update_todo() throws Exception {
        Todo todo = new Todo(null, "Buy milk", false);
        todo = todoRepository.save(todo);

        String requestBody = """
        {
         "text": "Buy snacks", "done": true
        }
       """;

        MockHttpServletRequestBuilder request = put("/todos/" + todo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todo.getId()))
                .andExpect(jsonPath("$.text").value("Buy snacks"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_return_updated_todo_and_ignore_provided_id_in_body_when_update_todo_and_request_body_has_id() throws Exception {
        Todo todo = new Todo(null, "Buy milk", false);
        todo = todoRepository.save(todo);

        String requestBody = """
            { "id": "456", "text": "Buy snacks", "done": true }
        """;

        MockHttpServletRequestBuilder request = put("/todos/" + todo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todo.getId()))
                .andExpect(jsonPath("$.text").value("Buy snacks"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_response_not_found_when_update_a_non_exist_todo() throws Exception {
        String requestBody = """
        {
         "text": "Buy snacks", "done": true
        }
       """;

        MockHttpServletRequestBuilder request = put("/todos/213")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

}
