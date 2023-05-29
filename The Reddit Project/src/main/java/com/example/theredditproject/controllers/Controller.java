package com.example.theredditproject.controllers;

import com.example.theredditproject.exceptions.PostException;
import com.example.theredditproject.exceptions.UserException;
import com.example.theredditproject.models.Post;
import com.example.theredditproject.models.User;
import com.example.theredditproject.services.PostServices;
import com.example.theredditproject.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class Controller {
    private PostServices postServices;
    private UserServices userServices;

    @Autowired
    public Controller(PostServices postServices, UserServices userServices) {
        this.postServices = postServices;
        this.userServices = userServices;
    }

    // Co je lepší používat na errory?
    // 1) try-catch
    // 2) .orElseThrow
    // 3) DTOs classy

    // + otázka na handling errors - používat v Controlleru / services?

    @GetMapping("/posts")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok().body(postServices.getAll());
        } catch (PostException exception) {
            return ResponseEntity.status(exception.getStatusCode()).body(new PostException(exception.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postServices.getOne(id);
        if (post != null) {
            return ResponseEntity.ok().body(postServices.getOne(id));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(
            @RequestBody Post post,
            @RequestHeader(value = "user", required = false) String headerValue) {

        if (headerValue != null) {
            User user = new User();
            user.setName(headerValue);
            post.setUser(user);
            userServices.save(user);
        }

        postServices.create(post);
        return ResponseEntity.ok().body("Post was created successfully.");
    }

    @PutMapping("/posts/{id}/upvote")
    public ResponseEntity<?> upvote(
            @PathVariable("id") Long id,
            @RequestHeader(value = "user", required = false) String headerValue) {

        if (headerValue != null) {

            User user = userServices.getUserByName(headerValue);
            if (user != null) {
                // Do something (like can vote only once?)
            }
        }

        postServices.upVote(id);
        return ResponseEntity.ok().body(postServices.getOne(id));
    }

    @PutMapping("/posts/{id}/downvote")
    public ResponseEntity<?> downvote(
            @PathVariable("id") Long id,
            @RequestHeader(value = "user", required = false) String headerValue) {

        if (headerValue != null) {

            User user = userServices.getUserByName(headerValue);
            if (user != null) {
                // Do something (like can vote only once?)
            }
        }

        postServices.downVote(id);
        return ResponseEntity.ok().body(postServices.getOne(id));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestBody Post updatedPost,
            @RequestHeader(value = "user", required = false) String headerValue) {

        if (headerValue != null) {
            User user = userServices.getUserByName(headerValue);
            if (user != null) {
                updatedPost.setUser(user);
                updatedPost.setTimestamp(LocalDateTime.now());
            }
        }

        Post post = postServices.update(id, updatedPost);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        postServices.delete(id);
        return ResponseEntity.ok().body("Post was deleted successfully.");
    }

    @PutMapping("/posts/{id}/vote")
    public ResponseEntity<?> vote(
            @PathVariable("id") Long id,
            @RequestHeader(value = "user") String username,
            @RequestParam("value") int value) {

        try {
            postServices.vote(id, username, value);
            return ResponseEntity.ok().body("Vote recorded successfully.");
        } catch (UserException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/posts/users/{username}/posts/{id}")
    public ResponseEntity<?> deletePosts(
            @PathVariable("username") String username,
            @PathVariable("id") Long id) {

        try {
            User user = userServices.getUserByID(id);
            postServices.deletePosts(user);
            return ResponseEntity.ok().body("Post was deleted successfully.");
        } catch (PostException | UserException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    // User endpoints

    @PostMapping("/posts/users")
    public ResponseEntity<?> createUser(
            @RequestHeader(value = "user") String headerValue) {

        User user = new User();
        user.setName(headerValue);
        userServices.save(user);

        return ResponseEntity.ok().body("User was created successfully.");
    }

    @GetMapping("/posts/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok().body(userServices.getAllUsers());
        } catch (PostException exception) {
            return ResponseEntity.status(exception.getStatusCode()).body(new UserException(("User not found.")));
        }
    }

    @GetMapping("/posts/user")
    public ResponseEntity<?> getUserByName(@RequestHeader(value = "user", required = false) String headerValue) {
        User user = userServices.getUserByName(headerValue);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
