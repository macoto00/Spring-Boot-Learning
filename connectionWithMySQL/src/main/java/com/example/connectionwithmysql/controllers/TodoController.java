package com.example.connectionwithmysql.controllers;

import com.example.connectionwithmysql.models.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping({"/", "/list", "/list-done"})
    public List<Todo> list(HttpServletRequest request) {
        List<Todo> todos;

        if (request.getRequestURI().endsWith("/list-done")) {
            Query query = entityManager.createNativeQuery("SELECT * FROM todo WHERE done = 1", Todo.class);
            todos = query.getResultList();
        } else {
            Query query = entityManager.createNativeQuery("SELECT * FROM todo", Todo.class);
            todos = query.getResultList();
        }

        return todos;
    }
    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> createTodo(@ModelAttribute Todo todo, RedirectAttributes redirectAttributes) {

        String title = todo.getTitle();
        boolean urgent = todo.isUrgent();
        boolean done = todo.isDone();

        if (title == null || title.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (done) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Todo newTodo = new Todo();
        newTodo.setTitle(title);
        newTodo.setUrgent(urgent);
        newTodo.setDone(done);

        entityManager.persist(newTodo);

        redirectAttributes.addAttribute("id", newTodo.getId());
        return ResponseEntity.ok(newTodo);
    }

    // addition

    @GetMapping("/todo/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") Long id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") Long id, @RequestBody TodoRequest todoRequest) {
        String title = todoRequest.getTitle();

        if (title == null || title.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Todo existingTodo = todoRepository.findById(id).orElse(null);
        if (existingTodo == null) {
            return ResponseEntity.notFound().build();
        }

        existingTodo.setTitle(title);
        existingTodo.setUrgent(todoRequest.isUrgent());
        existingTodo.setDone(todoRequest.isDone());

        todoRepository.save(existingTodo);

        return ResponseEntity.ok(existingTodo);
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id) {
        Todo existingTodo = todoRepository.findById(id).orElse(null);
        if (existingTodo == null) {
            return ResponseEntity.notFound().build();
        }

        todoRepository.delete(existingTodo);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/todo/expired")
    public List<Todo> getExpiredTodos() {
        LocalDate currentDate = LocalDate.now();
        return todoRepository.findByDueDateBeforeAndDoneFalse(currentDate);
    }

    @GetMapping("/todo/search")
    public List<Todo> searchTodosByAssigneeAndKeyword(
            @RequestParam("assignee") String assignee,
            @RequestParam("keyword") String keyword) {
        return todoRepository.findByAssigneeNameContainingIgnoreCaseAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndTitleContainingIgnoreCase(
                assignee, keyword, keyword);
    }

    @PatchMapping("/todo/updateDueDate")
    public int updateDueDateByAssigneeAndDays(
            @RequestParam("assignee") String assignee,
            @RequestParam("days") int days) {
        LocalDate currentDate = LocalDate.now();
        LocalDate newDueDate = currentDate.plusDays(days);
        return todoRepository.updateDueDateByAssigneeAndDueDate(assignee, currentDate, newDueDate);
    }
}
