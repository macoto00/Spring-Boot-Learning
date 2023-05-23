package com.example.listingtodo.controllers;

import com.example.listingtodo.models.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @PersistenceContext
    private EntityManager entityManager;
    @RequestMapping({"/", "/list"})
    public List<Todo> list() {
        Query query = entityManager.createNativeQuery("SELECT * FROM todos");
        List<Todo> todos = query.getResultList();
        return todos;
    }
}
