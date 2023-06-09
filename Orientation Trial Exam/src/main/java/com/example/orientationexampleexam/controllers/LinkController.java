package com.example.orientationexampleexam.controllers;

import com.example.orientationexampleexam.DTOS.LinkDTO;
import com.example.orientationexampleexam.DTOS.SecretCodeDTO;
import com.example.orientationexampleexam.models.IncorrectSecretCodeException;
import com.example.orientationexampleexam.models.LinkNotFoundException;
import com.example.orientationexampleexam.services.LinkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LinkController {
    private final LinkServices linkServices;

    @Autowired
    public LinkController(LinkServices linkServices) {
        this.linkServices = linkServices;
    }

    @GetMapping("/api/links")
    public ResponseEntity<?> getAllLinks() {
        List<LinkDTO> links = linkServices.getAllLinks();
        return ResponseEntity.ok(links);
    }

    @PostMapping("/api/links")
    public ResponseEntity<?> createLink(@RequestBody LinkDTO linkDTO) {
        try {
            LinkDTO createdLink = linkServices.createLink(linkDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLink);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/a/{alias}")
    public ResponseEntity<?> redirectLink(@PathVariable String alias) {
        String url = linkServices.getURLByAlias(alias);
        if (url != null) {
            linkServices.incrementHitCount(alias);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", url).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLink(@PathVariable Long id, @RequestBody SecretCodeDTO secretCodeDTO) {
        try {
            linkServices.deleteLink(id, secretCodeDTO.getSecretCode());
            return ResponseEntity.noContent().build();
        } catch (LinkNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IncorrectSecretCodeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
