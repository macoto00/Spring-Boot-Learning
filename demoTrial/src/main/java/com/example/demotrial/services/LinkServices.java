package com.example.demotrial.services;

import com.example.demotrial.DTOS.LinkDTO;

import java.util.List;

public interface LinkServices {
    List<LinkDTO> getAllLinks();
    void createLink(LinkDTO linkDTO);
    String getUrlByAlias(String alias);
    void incrementHitCount(String alias);
    void deleteLink(Long id, String secretCode);

}
