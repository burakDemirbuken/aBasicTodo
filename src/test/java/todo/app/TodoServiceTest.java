package todo.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Test
    void createTodoDelegatesToRepository() {
        TodoRepository repo = mock(TodoRepository.class);   // sahte repository
        Todo todo = new Todo("Süt al", "2 litre");
        TodoResponse entry = new TodoResponse(0, todo.title(), todo.description(), todo.completed());
        when(repo.create(todo)).thenReturn(entry);           // "create çağrılırsa şunu döndür"

        TodoService service = new TodoService(repo);          // sahte repo'yu elle veriyoruz
        TodoResponse result = service.createTodo(todo);

        assertEquals(entry, result);
        verify(repo).create(todo);                            // gerçekten çağrıldı mı kontrol et
    }

    @Test
    void getTodoByIdPropagatesNotFound() {
        TodoRepository repo = mock(TodoRepository.class);
        when(repo.findById(5)).thenThrow(new IllegalArgumentException("bulunamadı"));

        TodoService service = new TodoService(repo);

        assertThrows(IllegalArgumentException.class, () -> service.getTodoById(5));
    }
}