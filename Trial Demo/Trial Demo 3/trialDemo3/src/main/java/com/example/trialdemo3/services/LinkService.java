package com.example.trialdemo3.services;

import com.example.trialdemo3.DTOS.LinkDTO;
import com.example.trialdemo3.models.Link;

import java.util.List;

public interface LinkService {
    List<LinkDTO> getAllLinks();
    void createLink(Link link);
    String getUrlByAlias(String alias);
    boolean aliasIsPresent(String alias);
    void incrementHitCount(String alias);
    void deleteLink(Long id, String secretCode);
}
