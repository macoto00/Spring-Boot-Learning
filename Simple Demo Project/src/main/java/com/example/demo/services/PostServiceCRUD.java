package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceCRUD implements PostServices {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceCRUD(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Iterable<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void updatePost(Long id, Post post) {
        Post postToUpdate = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setUrl(post.getUrl());
        postRepository.save(postToUpdate);
    }

    @Override
    public void deletePost(Long id) {
        Post postToDelete = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(postToDelete);
    }
}
