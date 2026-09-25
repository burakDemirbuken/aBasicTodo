package todo.app;

import todo.app.TodoService;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import todo.app.TodoResponse;
import java.net.URI;

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
		try
		{
			return ResponseEntity.ok(todoService.getTodoById(id));
		}
		catch (IllegalArgumentException e)
		{
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/todos")
	public ResponseEntity<TodoResponse> createTodo(@RequestBody Todo todo)
	{
		System.out.println("Creating todo: " + todo);
		TodoResponse newtodo = todoService.createTodo(todo);
		URI location = URI.create("/todos/" + newtodo.id());
		return ResponseEntity.created(location).body(newtodo);
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<TodoResponse> updateTodo(@PathVariable int id, @RequestBody Todo todo) {
		try {
			return ResponseEntity.ok(todoService.updateTodo(id, todo));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}


	@DeleteMapping("/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable int id) {
		try {
			todoService.deleteTodo(id);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
}