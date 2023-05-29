package com.example.theredditproject.services;

import com.example.theredditproject.exceptions.UserException;
import com.example.theredditproject.models.Post;
import com.example.theredditproject.models.User;
import com.example.theredditproject.models.Vote;
import com.example.theredditproject.repositories.PostRepository;
import com.example.theredditproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServicesCRUD implements PostServices {
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostServicesCRUD(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<Post> getAll() {
        return postRepository.findAll();
    }


    @Override
    public Post getOne(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    public void create(Post post) {
        postRepository.save(post);
    }

    @Override
    public void upVote(Long id) {
        Post postToUpdate = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postToUpdate.setVote(postToUpdate.getVote() + 1);
        postRepository.save(postToUpdate);
    }

    @Override
    public void downVote(Long id) {
        Post postToDownvote = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postToDownvote.setVote(postToDownvote.getVote() - 1);
        postRepository.save(postToDownvote);
    }

    @Override
    public void delete(Long id) {
        Post postToDelete = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postRepository.delete(postToDelete);
    }

    @Override
    public Post update(Long id, Post post) {
        Post postToUpdate = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setUrl(post.getUrl());
        postToUpdate.setTimestamp(post.getTimestamp());
        postToUpdate.setScore(post.getScore());

        postToUpdate.setUser(post.getUser());

        return postRepository.save(postToUpdate);
    }

    @Override
    public void vote(Long postId, String username, int value) {
        Post post = postRepository.findByIdAndUser_Name(postId, username);
        if (post == null) {
            throw new IllegalArgumentException("Post not found or user is not the owner");
        }

        // Post post = postRepository.findById(postId)
        //        .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UserException("No user found for name: " + username);
        }

        Integer voteCount = post.getVote();
        if (voteCount == null) {
            voteCount = 0;
        } else if (voteCount == value) {
            throw new UserException("User has already voted on this post");
        }

        post.setVote(value);
        post.setTimestamp(LocalDateTime.now());
        postRepository.save(post);
    }

    @Override
    public void deletePosts(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getUser().getName().equals(username)) {
            throw new UserException("You can only delete your own posts");
        }

        postRepository.delete(post);
    }
}
