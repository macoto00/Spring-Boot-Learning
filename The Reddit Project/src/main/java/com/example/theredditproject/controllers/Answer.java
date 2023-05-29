//package com.example.theredditproject.controllers;
//
//import com.example.theredditproject.exceptions.PostException;
//import com.example.theredditproject.models.Post;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//public class Answer {
//    @PostMapping("/posts")
//    public ResponseEntity<?> createPost(@RequestBody Post post) {
//        try {
//            postServices.post(post);
//            return ResponseEntity.ok().body("Post was created successfully.");
//            // return new ResponseEntity<>(HttpStatus.CREATED);
//        } catch (PostException exception) {
//            return ResponseEntity.status(exception.getStatusCode()).body(new PostException(exception.getMessage(), HttpStatus.BAD_REQUEST));
//        }
//    }
//    @PutMapping("/posts/{id}/upvote")
//    public ResponseEntity<?> upvote(@PathVariable("id") Long id) {
//        try {
//            postServices.upVote(id);
//            return ResponseEntity.ok().body(postServices.getOnePost(id));
//        } catch (PostException exception) {
//            return ResponseEntity.status(exception.getStatusCode()).body(new PostException(exception.getMessage(), HttpStatus.BAD_REQUEST));
//        }
//    }
//
//    @PutMapping("/posts/{id}/downvote")
//    public ResponseEntity<?> downvote(@PathVariable("id") Long id) {
//        try {
//            postServices.downVote(id);
//            return ResponseEntity.ok().body(postServices.getOnePost(id));
//        } catch (PostException exception) {
//            return ResponseEntity.status(exception.getStatusCode()).body(new PostException(exception.getMessage(), HttpStatus.BAD_REQUEST));
//        }
//    }
//}
