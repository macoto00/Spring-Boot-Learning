package com.example.trialdemo3.controllers;

import com.example.trialdemo3.DTOS.LinkDTO;
import com.example.trialdemo3.DTOS.SecretCode;
import com.example.trialdemo3.models.Link;
import com.example.trialdemo3.services.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> incrementHitCount(@PathVariable String alias, HttpServletResponse response) {
        if (linkService.aliasIsPresent(alias)) {
            linkService.incrementHitCount(alias);
            String url = linkService.getUrlByAlias(alias);
            response.setHeader("Location", url);
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", url).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/api/links")
    public ResponseEntity<?> getAllLinks() {
        List<LinkDTO> listToReturn = linkService.getAllLinks();
        return ResponseEntity.ok().body(listToReturn);
    }

    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<?> deleteLink(
            @PathVariable Long id,
            @RequestBody SecretCode secretCode) {
        Optional<Link> linkOptional = linkService.findLinkById(id);
        if (linkOptional.isPresent()) {
            Link link = linkOptional.get();
            if (secretCode.getSecretCode().equals(link.getSecretCode())) {
                linkService.deleteLink(id, secretCode);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
