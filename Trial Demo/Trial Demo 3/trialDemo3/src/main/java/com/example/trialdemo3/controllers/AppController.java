package com.example.trialdemo3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

    @PostMapping("/api/links")
    public ResponseEntity<?> createLink (@RequestParam String alias) {
        return null;
    }

    @GetMapping("/a/{alias}")
    public ResponseEntity<?> incrementHitCount(@RequestParam String alias) {
        return null;
    }

    @GetMapping("/api/links")
    public ResponseEntity<?> getAllLinks() {
        return null;
    }

    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<?> deleteLink(
            @RequestParam Long id,
            @RequestBody String alias) {
        return null;
    }
}
