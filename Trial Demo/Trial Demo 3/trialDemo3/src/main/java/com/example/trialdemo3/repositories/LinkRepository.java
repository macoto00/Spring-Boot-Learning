package com.example.trialdemo3.repositories;

import com.example.trialdemo3.models.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
    Link findByAlias(String alias);
}
