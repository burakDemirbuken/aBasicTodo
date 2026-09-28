package todo.app;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TodoRepository {

    private final TodoJpaRepository jpaRepository;

    public TodoRepository(TodoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<TodoResponse> findAll() {
        return jpaRepository.findAll().stream()
                .map(e -> new TodoResponse(e.getId(), e.getTitle(), e.getDescription(), e.isCompleted()))
                .collect(Collectors.toList());
    }

    public TodoResponse findById(int id) {
        TodoEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        return new TodoResponse(entity.getId(), entity.getTitle(), entity.getDescription(), entity.isCompleted());
    }

    public TodoResponse create(Todo todo) {
        TodoEntity entity = new TodoEntity(todo.title(), todo.description(), todo.completed());
        TodoEntity saved = jpaRepository.save(entity);
        return new TodoResponse(saved.getId(), saved.getTitle(), saved.getDescription(), saved.isCompleted());
    }

    public TodoResponse update(int id, Todo todo) {
        TodoEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        entity.setTitle(todo.title());
        entity.setDescription(todo.description());
        entity.setCompleted(todo.completed());
        TodoEntity saved = jpaRepository.save(entity);
        return new TodoResponse(saved.getId(), saved.getTitle(), saved.getDescription(), saved.isCompleted());
    }

    public void delete(int id) {
        if (!jpaRepository.existsById(id))
            throw new TodoNotFoundException(id);
        jpaRepository.deleteById(id);
    }
}