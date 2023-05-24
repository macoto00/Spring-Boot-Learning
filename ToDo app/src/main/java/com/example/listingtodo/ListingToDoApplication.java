package com.example.listingtodo;

import com.example.listingtodo.models.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ListingToDoApplication implements CommandLineRunner {
    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(ListingToDoApplication.class, args);
    }
    @Transactional
    public void run(String... args) throws Exception {
        List<Todo> todos = new ArrayList<>(Arrays.asList(
                new Todo("Buy milk", false, false),
                new Todo("Walk a dog", true, true),
                new Todo("Be a hero", true, false)
        ));

        // ... and we persist each of them in database (save them to database)
        todos.forEach(todo -> entityManager.persist(todo));
    }
}
