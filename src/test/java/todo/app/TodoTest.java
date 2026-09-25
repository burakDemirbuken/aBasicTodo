package todo.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import todo.app.Todo;
import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @Test
    void blankTitleThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Todo("", "açıklama"));
    }

    @Test
    void blankDescriptionThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Todo("Başlık", ""));
    }

	@Test
	void nullTitleThrows() {
		assertThrows(IllegalArgumentException.class, () -> new Todo(null, "açıklama"));
	}

	@Test
	void nullDescriptionThrows() {
		assertThrows(IllegalArgumentException.class, () -> new Todo("Başlık", null));
	}

	@Test
	void nullCompletedIsAllowed() {
		assertDoesNotThrow(() -> new Todo("Başlık", "açıklama", null));
	}

	@Test
	void completedDefaultsToFalse() {
		Todo todo = new Todo("Başlık", "açıklama", null);
		assertFalse(todo.completed());
	}

    @Test
    void convenienceConstructorDefaultsToNotCompleted() {
        Todo todo = new Todo("Süt al", "2 litre");
        assertFalse(todo.completed());
    }
}