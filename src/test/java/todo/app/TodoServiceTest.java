package todo.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Test
    void createTodoDelegatesToRepository() {
		TodoRepository repo = mock(TodoRepository.class);
		TodoService service = new TodoService(repo);

		CreateTodoRequest request = new CreateTodoRequest("Test Title", "Test Description");
		Todo todo = new Todo(request.title(), request.description());
		TodoResponse expectedResponse = new TodoResponse(1, todo.title(), todo.description(), false);

		when(repo.create(any(Todo.class))).thenReturn(expectedResponse);

		TodoResponse actualResponse = service.createTodo(request);

		assertEquals(expectedResponse, actualResponse);
		verify(repo).create(any(Todo.class));
    }

    @Test
    void getTodoByIdPropagatesNotFound() {
        TodoRepository repo = mock(TodoRepository.class);
        when(repo.findById(5)).thenThrow(new TodoNotFoundException(5));

        TodoService service = new TodoService(repo);

        assertThrows(TodoNotFoundException.class, () -> service.getTodoById(5));
    }
}
