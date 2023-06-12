package com.example.trialdemo3.services;

import com.example.trialdemo3.DTOS.LinkDTO;
import com.example.trialdemo3.DTOS.SecretCode;
import com.example.trialdemo3.models.Link;

import java.util.List;
import java.util.Optional;

public interface LinkService {
    List<LinkDTO> getAllLinks();
    void createLink(Link link);
    Optional<Link> findByAlias(String alias);
    String getUrlByAlias(String alias);
    boolean aliasIsPresent(String alias);
    void incrementHitCount(String alias);
    void deleteLink(Long id, SecretCode secretCode);
    Optional<Link> findLinkById(Long id);
}
