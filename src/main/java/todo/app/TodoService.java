package todo.app;

import org.springframework.stereotype.Service;
import java.util.List;

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

	public TodoResponse createTodo(CreateTodoRequest todo) {
		return todoRepository.create(new Todo(todo.title(), todo.description()));
	}

	public TodoResponse updateTodo(int id, Todo todo) {
		return todoRepository.update(id, todo);
	}

	public void deleteTodo(int id) {
		todoRepository.delete(id);
	}

}
