package todo.app;

public class TodoNotFoundException extends RuntimeException {
	public TodoNotFoundException(int id) {
		super("Todo with id " + id + " not found");
	}
}
