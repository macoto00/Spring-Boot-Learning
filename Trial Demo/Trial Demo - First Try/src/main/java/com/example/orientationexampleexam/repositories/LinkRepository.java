package com.example.orientationexampleexam.repositories;

import com.example.orientationexampleexam.models.Link;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Long> {
    boolean existsByAlias(String alias);

    Link findByAlias(String alias);
}
