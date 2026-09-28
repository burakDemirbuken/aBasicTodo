package todo.app;

import java.util.List;
import java.net.URI;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

@RestController
class TodoController {
	private final TodoService todoService;

	public TodoController(TodoService todoService)
	{
		this.todoService = todoService;
	}

	@GetMapping("/todos")
	public ResponseEntity<List<TodoResponse>> getTodos()
	{
		return ResponseEntity.ok(todoService.getTodos());
	}

	@GetMapping("/todos/{id}")
	public ResponseEntity<TodoResponse> getTodoById(@PathVariable int id)
	{
		return ResponseEntity.ok(todoService.getTodoById(id));
	}

	@PostMapping("/todos")
	public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest todo)
	{
		TodoResponse newtodo = todoService.createTodo(todo);
		URI location = URI.create("/todos/" + newtodo.id());
		return ResponseEntity.created(location).body(newtodo);
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<TodoResponse> updateTodo(@PathVariable int id, @Valid @RequestBody UpdateTodoRequest request) {
		Todo todo = new Todo(request.title(), request.description(), request.completed());
		return ResponseEntity.ok(todoService.updateTodo(id, todo));
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable int id) {
		todoService.deleteTodo(id);
		return ResponseEntity.noContent().build();
	}
}
