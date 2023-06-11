package com.example.trialdemo3.services;

import com.example.trialdemo3.DTOS.LinkDTO;
import com.example.trialdemo3.models.Link;
import com.example.trialdemo3.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class LinkServiceCRUD implements LinkService {
    private final LinkRepository linkRepository;
    @Autowired
    public LinkServiceCRUD(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public List<LinkDTO> getAllLinks() {
        List<Link> listOfAllLinks = (List<Link>) linkRepository.findAll();
        List<LinkDTO> listOfAllLinkDTOs = new ArrayList<>();
        LinkDTO linkDTO = new LinkDTO();
        for (Link link : listOfAllLinks) {
            linkDTO.setId(link.getId());
            linkDTO.setUrl(link.getUrl());
            linkDTO.setHitCount(link.getHitCount());
            linkDTO.setAlias(link.getAlias());
            listOfAllLinkDTOs.add(linkDTO);
        }
        return listOfAllLinkDTOs;
    }

    @Override
    public void createLink(Link link) {
        Link newLink = new Link();
        newLink.setUrl(link.getUrl());
        newLink.setAlias(link.getAlias());
        newLink.setHitCount(link.getHitCount());
        newLink.setSecretCode(generateSecretCode());
        linkRepository.save(newLink);
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

    private String generateSecretCode() {
        return String.format("%04d", new Random().nextInt(10000));
    }
}
