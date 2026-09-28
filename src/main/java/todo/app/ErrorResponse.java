package todo.app;

import java.time.Instant;

public record ErrorResponse(Instant timestamp, int status, String message) {}
