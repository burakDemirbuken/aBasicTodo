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
        when(todoService.getTodoById(99)).thenThrow(new TodoNotFoundException(99));

        mockMvc.perform(get("/todos/99"))
            .andExpect(status().isNotFound());
    }

	@Test
	void createTodoReturns400WhenTitleIsBlank() throws Exception {
		mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"title":"","description":"2 litre","completed":false}
					"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createTodoReturns400WhenDescriptionIsBlank() throws Exception
	{
		mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"title":"Süt al","description":"","completed":false}
					"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createTodoReturns400WhenTitleIsMissing() throws Exception
	{
		mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"description":"2 litre","completed":false}
					"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createTodoReturns400WhenDescriptionIsMissing() throws Exception
	{
		mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"title":"Süt al","completed":false}
					"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void updateTodoReturns400WhenTitleIsBlank() throws Exception {
		// Bu, eski kodda 404 dönen bug'ın testi: artık doğru şekilde 400 dönmeli.
		mockMvc.perform(put("/todos/0")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"title":"","description":"2 litre","completed":false}
					"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void updateTodoReturns404WhenMissing() throws Exception {
		when(todoService.updateTodo(eq(99), any())).thenThrow(new TodoNotFoundException(99));

		mockMvc.perform(put("/todos/99")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"title":"Süt al","description":"2 litre","completed":false}
					"""))
				.andExpect(status().isNotFound());
	}
}
