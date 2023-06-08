package com.example.demo.services;

import com.example.demo.models.Post;

public interface PostServices {
    Iterable<Post> getAll();
    void createPost(Post post);
    void updatePost(Long id, Post post);
    void deletePost(Long id);
}
