package com.example.theredditproject.repositories;

import com.example.theredditproject.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByIdAndUser_Name(Long id, String username);

}
