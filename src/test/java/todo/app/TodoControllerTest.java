package todo.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;   // gerçek Service yerine sahte bean

    @Test
    void createTodoReturns201() throws Exception {
        Todo todo = new Todo("Süt al", "2 litre");
        when(todoService.createTodo(any())).thenReturn(new TodoResponse(0, todo.title(), todo.description(), todo.completed()));

        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"title":"Süt al","description":"2 litre","completed":false}
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Süt al"));
    }

    @Test
    void getTodoByIdReturns404WhenMissing() throws Exception {
        when(todoService.getTodoById(99)).thenThrow(new IllegalArgumentException("bulunamadı"));

        mockMvc.perform(get("/todos/99"))
            .andExpect(status().isNotFound());
    }
}