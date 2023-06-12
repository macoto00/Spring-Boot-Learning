package com.example.orientationexampleexam.services;

import com.example.orientationexampleexam.DTOS.LinkDTO;

import java.util.List;

public interface LinkServices {
    List<LinkDTO> getAllLinks();

    LinkDTO createLink(LinkDTO linkDTO);

    String getURLByAlias(String alias);

    void incrementHitCount(String alias);

    void deleteLink(Long id, String secretCode);
}
