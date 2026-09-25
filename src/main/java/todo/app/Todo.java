package todo.app;

record Todo(String title, String description, Boolean completed) {
	Todo(String title, String description) {
		this(title, description, false);
	}

	Todo
	{
		if (title == null || title.isBlank())
			throw new IllegalArgumentException("Title must not be blank");
		if (description == null || description.isBlank())
			throw new IllegalArgumentException("Description must not be blank");
		completed = completed == null ? false : completed;
	}
}
