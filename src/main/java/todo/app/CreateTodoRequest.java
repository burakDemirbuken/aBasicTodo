package todo.app;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoRequest(
	@NotBlank(message = "Title cannot be blank") String title,
	@NotBlank(message = "Description cannot be blank") String description
) {}
