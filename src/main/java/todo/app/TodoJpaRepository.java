package todo.app;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<TodoEntity, Integer> {
}