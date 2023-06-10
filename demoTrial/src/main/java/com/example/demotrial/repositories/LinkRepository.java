package com.example.demotrial.repositories;

import com.example.demotrial.models.Link;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
//    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM Link l WHERE l.alias = :alias) THEN true ELSE false END")
//    boolean aliasExists(@Param("alias") String alias);
    Link findByAlias(String alias);
}
