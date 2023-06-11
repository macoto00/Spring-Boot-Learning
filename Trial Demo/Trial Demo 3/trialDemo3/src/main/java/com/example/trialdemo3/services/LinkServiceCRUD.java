package com.example.trialdemo3.services;

import com.example.trialdemo3.models.Link;
import com.example.trialdemo3.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceCRUD implements LinkService {
    private final LinkRepository linkRepository;
    @Autowired
    public LinkServiceCRUD(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public List<Link> getAllLinks() {
        return null;
    }

    @Override
    public void createLink(Link link) {

    }

    @Override
    public String getUrlByAlias(String alias) {
        return null;
    }

    @Override
    public void incrementHitCount(String alias) {

    }

    @Override
    public void deleteLink(Long id, String secretCode) {

    }
}
