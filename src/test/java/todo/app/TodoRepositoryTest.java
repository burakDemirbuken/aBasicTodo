package todo.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoRepositoryTest {

    private TodoRepository repository;
    private TodoJpaRepository jpaRepository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(TodoJpaRepository.class);
        repository = new TodoRepository(jpaRepository);
    }

    @Test
    void createAssignsIncrementingId() {
        TodoEntity firstEntity = mock(TodoEntity.class);
        TodoEntity secondEntity = mock(TodoEntity.class);
        when(firstEntity.getId()).thenReturn(0);
        when(secondEntity.getId()).thenReturn(1);
        when(firstEntity.getTitle()).thenReturn("Süt al");
        when(firstEntity.getDescription()).thenReturn("2 litre");
        when(secondEntity.getTitle()).thenReturn("Ekmek al");
        when(secondEntity.getDescription()).thenReturn("1 adet");
        when(jpaRepository.save(any(TodoEntity.class))).thenReturn(firstEntity, secondEntity);

        TodoResponse first = repository.create(new Todo("Süt al", "2 litre"));
        TodoResponse second = repository.create(new Todo("Ekmek al", "1 adet"));

        assertEquals(0, first.id());
        assertEquals(1, second.id());
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(jpaRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> repository.findById(999));
    }

    @Test
    void deleteRemovesTodo() {
        TodoEntity entity = mock(TodoEntity.class);
        when(entity.getId()).thenReturn(0);
        when(entity.getTitle()).thenReturn("Süt al");
        when(entity.getDescription()).thenReturn("2 litre");
        when(jpaRepository.save(any(TodoEntity.class))).thenReturn(entity);
        when(jpaRepository.existsById(0)).thenReturn(true);
        when(jpaRepository.findById(0)).thenReturn(Optional.empty());

        TodoResponse created = repository.create(new Todo("Süt al", "2 litre"));
        repository.delete(created.id());

        assertThrows(TodoNotFoundException.class, () -> repository.findById(created.id()));
    }
}
