package todo.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoViewController {
    private final TodoService todoService;

    public TodoViewController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos-view")
    public String listTodos(Model model) {
        model.addAttribute("todos", todoService.getTodos());
        return "todos";
    }

    @GetMapping("/todos-view/new")
    public String newTodoForm() {
        return "todo-form";
    }

    @PostMapping("/todos-view")
    public String createTodoFromForm(@ModelAttribute CreateTodoRequest request) {
        todoService.createTodo(request);
        return "redirect:/todos-view";
    }
}