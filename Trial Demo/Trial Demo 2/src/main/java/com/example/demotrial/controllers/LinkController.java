package com.example.demotrial.controllers;

import com.example.demotrial.DTOS.LinkDTO;
import com.example.demotrial.services.LinkServices;
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

    @PostMapping("/api/links")
    public ResponseEntity<?> createLink(@RequestBody LinkDTO linkDTO) {
        linkServices.createLink(linkDTO);
        return ResponseEntity.ok().body(linkDTO);
    }

    @GetMapping("/a/{alias}")
    public ResponseEntity<?> incrementHitCount(@PathVariable String alias) {
        String url = linkServices.getUrlByAlias(alias);
        if (url != null) {
            linkServices.incrementHitCount(alias);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", url).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/api/links")
    public ResponseEntity<?> getAllLinks() {
        List<LinkDTO> listOfAllLinks = linkServices.getAllLinks();
        return ResponseEntity.ok().body(listOfAllLinks);
    }

    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<?> deleteLink(@PathVariable Long id, @RequestBody String secretCode) {
        try {
            linkServices.deleteLink(id, secretCode);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("forbidden")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: " + errorMessage);
            } else if (errorMessage.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found: " + errorMessage);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
            }
        }
    }
}
