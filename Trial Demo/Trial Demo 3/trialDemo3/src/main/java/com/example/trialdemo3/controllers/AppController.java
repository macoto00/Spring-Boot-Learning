package com.example.trialdemo3.controllers;

import com.example.trialdemo3.DTOS.LinkDTO;
import com.example.trialdemo3.models.Link;
import com.example.trialdemo3.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppController {
    private final LinkService linkService;

    @Autowired
    public AppController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/api/links")
    public ResponseEntity<?> createLink(@RequestBody Link link) {
        String aliasToCheck = link.getAlias();
        if (linkService.aliasIsPresent(aliasToCheck)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            linkService.createLink(link);
            return ResponseEntity.ok().body(link);
        }
    }

    @GetMapping("/a/{alias}")
    public ResponseEntity<?> incrementHitCount(@RequestParam String alias) {
        return null;
    }

    @GetMapping("/api/links")
    public ResponseEntity<?> getAllLinks() {
        List<LinkDTO> listToReturn = linkService.getAllLinks();
        return ResponseEntity.ok().body(listToReturn);
    }

    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<?> deleteLink(
            @RequestParam Long id,
            @RequestBody String alias) {
        return null;
    }
}
