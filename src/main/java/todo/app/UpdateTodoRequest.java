package todo.app;

import jakarta.validation.constraints.NotBlank;

public record UpdateTodoRequest(
	@NotBlank(message = "Title cannot be blank") String title,
	@NotBlank(message = "Description cannot be blank") String description,
	boolean completed
) {}
