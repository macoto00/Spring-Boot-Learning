package com.example.theredditproject.services;

import com.example.theredditproject.models.Post;

public interface PostServices {
    Iterable<Post> getAll();
    Post getOne(Long id);
    void create(Post post);
    void upVote(Long id);
    void downVote(Long id);
    void delete(Long id);
    Post update(Long id, Post post);
    void vote(Long postId, String username, int value);
    void deletePosts(Long id, String userName);
}
