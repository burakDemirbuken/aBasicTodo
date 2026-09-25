package todo.app;

import org.springframework.stereotype.Service;
import todo.app.TodoRepository;
import java.util.List;
import todo.app.TodoResponse;

@Service
class TodoService {
	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public List<TodoResponse> getTodos() {
		return todoRepository.findAll();
	}

	public TodoResponse getTodoById(int id) {
		return todoRepository.findById(id);
	}

	public TodoResponse createTodo(Todo todo) {
		return todoRepository.create(todo);
	}

	public TodoResponse updateTodo(int id, Todo todo) {
		return todoRepository.update(id, todo);
	}

	public void deleteTodo(int id) {
		todoRepository.delete(id);
	}

}