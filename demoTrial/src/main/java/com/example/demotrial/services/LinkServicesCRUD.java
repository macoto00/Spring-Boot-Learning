package com.example.demotrial.services;

import com.example.demotrial.DTOS.LinkDTO;
import com.example.demotrial.models.Link;
import com.example.demotrial.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LinkServicesCRUD implements LinkServices {

    private final LinkRepository linkRepository;

    @Autowired
    public LinkServicesCRUD(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public List<LinkDTO> getAllLinks() {
        List<Link> listOfAllLinks = (List<Link>) linkRepository.findAll();
        List<LinkDTO> listOfAllLinksDTO = new ArrayList<>();
        LinkDTO linkDTO = new LinkDTO();
        for (Link link : listOfAllLinks) {
            linkDTO.setId(link.getId());
            linkDTO.setUrl(link.getUrl());
            linkDTO.setHitCount(link.getHitCount());
            linkDTO.setAlias(link.getAlias());
            listOfAllLinksDTO.add(linkDTO);
        }
        return listOfAllLinksDTO;
    }

    @Override
    public void createLink(LinkDTO linkDTO) {
//        if (linkRepository.aliasExists(linkDTO.getAlias())) {
//            throw new RuntimeException("Alias is already in use");
//        }

        Optional<Link> maybeLink = Optional.ofNullable(linkRepository.findByAlias(linkDTO.getAlias()));
        if (maybeLink.isPresent()) {
            throw new RuntimeException("Alias is already in use");
        }
        Link link = new Link();
        link.setUrl(linkDTO.getUrl());
        link.setAlias(linkDTO.getAlias());
        link.setHitCount(linkDTO.getHitCount());
        link.setSecretCode(generateSecretCode());
        linkRepository.save(link);
    }

    @Override
    public String getUrlByAlias(String alias) {
        Link link = linkRepository.findByAlias(alias);
        if (link.getUrl() != null) {
            return link.getUrl();
        }
        throw new RuntimeException("Link not found");
    }

    @Override
    public void incrementHitCount(String alias) {
        Link link = linkRepository.findByAlias(alias);
        if (link != null) {
            link.setAlias(link.getAlias() + 1);
            linkRepository.save(link);
        }
        throw new RuntimeException("Link not found");
    }

    @Override
    public void deleteLink(Long id, String secretCode) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new RuntimeException("Link not found"));
        if (!Objects.equals(link.getSecretCode(), secretCode)) {
            throw new RuntimeException("The secret codes doesn't mach");
        }
        linkRepository.delete(link);
    }

    private String generateSecretCode() {
        return String.format("%04d", new Random().nextInt(10000));
    }
}
