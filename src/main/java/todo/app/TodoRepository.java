package todo.app;

import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;

@Repository
public class TodoRepository
{

	private final Map<Integer, Todo> todos;
	private int currentId;

	public TodoRepository() {
		this.todos = new HashMap<Integer, Todo>();
		this.currentId = -1;
	}

	public List<TodoResponse> findAll() {
		return todos.entrySet().stream()
				.map(entry -> new TodoResponse(entry.getKey(), entry.getValue().title(), entry.getValue().description(), entry.getValue().completed()))
				.collect(Collectors.toList());
	}

	public TodoResponse findById(int id) {
		Todo todo = todos.get(id);
		if (todo == null)
			throw new TodoNotFoundException(id);
		return new TodoResponse(id, todo.title(), todo.description(), todo.completed());
	}

	public TodoResponse create(Todo todo) {
		currentId++;
		todos.put(currentId, todo);
		return new TodoResponse(currentId, todo.title(), todo.description(), todo.completed());
	}

	public TodoResponse update(int id, Todo todo) {
		if (!todos.containsKey(id))
			throw new TodoNotFoundException(id);
		todos.put(id, todo);
		return new TodoResponse(id, todo.title(), todo.description(), todo.completed());
	}

	public void delete(int id) {
		if (!todos.containsKey(id))
			throw new TodoNotFoundException(id);
		todos.remove(id);
	}

}
