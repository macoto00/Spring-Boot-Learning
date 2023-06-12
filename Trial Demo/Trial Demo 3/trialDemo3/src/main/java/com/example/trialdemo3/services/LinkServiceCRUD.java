package com.example.trialdemo3.services;

import com.example.trialdemo3.DTOS.LinkDTO;
import com.example.trialdemo3.DTOS.SecretCode;
import com.example.trialdemo3.models.Link;
import com.example.trialdemo3.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
        return listOfAllLinks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LinkDTO convertToDTO(Link link) {
        LinkDTO linkDTO = new LinkDTO();
        linkDTO.setId(link.getId());
        linkDTO.setUrl(link.getUrl());
        linkDTO.setHitCount(link.getHitCount());
        linkDTO.setAlias(link.getAlias());
        return linkDTO;
    }

//    @Override
//    public List<LinkDTO> getAllLinks() {
//        List<Link> listOfAllLinks = (List<Link>) linkRepository.findAll();
//        List<LinkDTO> listOfAllLinkDTOs = new ArrayList<>();
//        LinkDTO linkDTO = new LinkDTO();
//        for (Link link : listOfAllLinks) {
//            linkDTO.setId(link.getId());
//            linkDTO.setUrl(link.getUrl());
//            linkDTO.setHitCount(link.getHitCount());
//            linkDTO.setAlias(link.getAlias());
//            listOfAllLinkDTOs.add(linkDTO);
//        }
//        return listOfAllLinkDTOs;
//    }

    @Override
    public void createLink(Link link) {
        String aliasToCheck = link.getAlias();
        if (linkRepository.existsByAlias(aliasToCheck)) {
            throw new RuntimeException("400 Bad Request");
        }
        link.setSecretCode(generateSecretCode());
        linkRepository.save(link);
    }

//    @Override
//    public void createLink(Link link) {
//        String aliasToCheck = link.getAlias();
//        List<Link> listOfAllLinks = (List<Link>) linkRepository.findAll();
//        if (listOfAllLinks.stream().map(Link::getAlias).anyMatch(aliasToCheck::equals)) {
//            throw new RuntimeException("400 Bad Request");
//        } else {
//            link.setSecretCode(generateSecretCode());
//            linkRepository.save(link);
//        }
//    }

    @Override
    public Optional<Link> findByAlias(String alias) {
        return Optional.ofNullable(linkRepository.findByAlias(alias));
    }

    @Override
    public boolean aliasIsPresent(String alias) {
        return linkRepository.existsByAlias(alias);
    }

//    @Override
//    public boolean aliasIsPresent(String alias) {
//        List<Link> listOfAllLinks = (List<Link>) linkRepository.findAll();
//        return listOfAllLinks.stream().anyMatch(link -> alias.equals(link.getAlias()));
//    }

    @Override
    public void incrementHitCount(String alias) {
        Optional<Link> linkOptional = findByAlias(alias);
        linkOptional.ifPresent(link -> {
            link.setHitCount(link.getHitCount() + 1);
            linkRepository.save(link);
        });
    }

//    @Override
//    public void incrementHitCount(String alias) {
//        Optional<Link> linkOptional = Optional.ofNullable(linkRepository.findByAlias(alias));
//        if (linkOptional.isPresent()) {
//            Link link = linkOptional.get();
//            link.setHitCount(link.getHitCount() + 1);
//            linkRepository.save(link);
//        }
//    }

    @Override
    public String getUrlByAlias(String alias) {
        return findByAlias(alias)
                .map(Link::getUrl)
                .orElseThrow(() -> new RuntimeException("404 Not Found"));
    }

//    @Override
//    public String getUrlByAlias(String alias) {
//        Optional<Link> linkOptional = findByAlias(alias);
//        if (linkOptional.isPresent()) {
//            return linkOptional.get().getUrl();
//        } else {
//            throw new RuntimeException("404 Not Found");
//        }
//    }

    @Override
    public void deleteLink(Long id, SecretCode secretCode) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("404 Not Found"));
        if (!secretCode.getSecretCode().equals(link.getSecretCode())) {
            throw new RuntimeException("403 Forbidden");
        }
        linkRepository.delete(link);
    }

//    @Override
//    public void deleteLink(Long id, SecretCode secretCode) {
//        Optional<Link> linkOptional = linkRepository.findById(id);
//        if (linkOptional.isPresent()) {
//            Link link = linkOptional.get();
//            if (secretCode.getSecretCode().equals(link.getSecretCode())) {
//                linkRepository.delete(link);
//            } else {
//                throw new RuntimeException("403 Forbidden");
//            }
//        } else {
//            throw new RuntimeException("404 Not Found");
//        }
//    }

    @Override
    public Optional<Link> findLinkById(Long id) {
        return linkRepository.findById(id);
    }

    private String generateSecretCode() {
        return String.format("%04d", new Random().nextInt(10000));
    }
}
