package todo.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodoRepositoryTest {

    private TodoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TodoRepository();   // her testten önce sıfırdan
    }

    @Test
    void createAssignsIncrementingId() {
        TodoResponse first = repository.create(new Todo("Süt al", "2 litre"));
        TodoResponse second = repository.create(new Todo("Ekmek al", "1 adet"));

        assertEquals(0, first.id());
        assertEquals(1, second.id());
    }

    @Test
    void findByIdThrowsWhenMissing() {
        assertThrows(IllegalArgumentException.class, () -> repository.findById(999));
    }

    @Test
    void deleteRemovesTodo() {
        TodoResponse created = repository.create(new Todo("Süt al", "2 litre"));
        repository.delete(created.id());

        assertThrows(IllegalArgumentException.class, () -> repository.findById(created.id()));
    }
}