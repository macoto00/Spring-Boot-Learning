package com.example.orientationexampleexam.services;

import com.example.orientationexampleexam.DTOS.LinkDTO;
import com.example.orientationexampleexam.models.IncorrectSecretCodeException;
import com.example.orientationexampleexam.models.Link;
import com.example.orientationexampleexam.models.LinkMapper;
import com.example.orientationexampleexam.models.LinkNotFoundException;
import com.example.orientationexampleexam.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LinkServiceCRUD implements LinkServices{
    private final LinkRepository linkRepository;

    @Autowired
    public LinkServiceCRUD(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<LinkDTO> getAllLinks() {
        List<Link> links = (List<Link>) linkRepository.findAll();
        return LinkMapper.mapToDTOList(links);
    }

    public LinkDTO createLink(LinkDTO linkDTO) {
        if (linkRepository.existsByAlias(linkDTO.getAlias())) {
            throw new RuntimeException("Your alias is already in use!");
        }

        String secretCode = generateSecretCode();

        Link link = LinkMapper.mapToEntity(linkDTO, secretCode);

        Link savedLink = linkRepository.save(link);
        return LinkMapper.mapToDTO(savedLink);
    }

    public String getURLByAlias(String alias) {
        Link link = linkRepository.findByAlias(alias);
        if (link != null) {
            return link.getUrl();
        }
        return null;
    }

    public void incrementHitCount(String alias) {
        Link link = linkRepository.findByAlias(alias);
        if (link != null) {
            link.setHitCount(link.getHitCount() + 1);
            linkRepository.save(link);
        }
    }

    public void deleteLink(Long id, String secretCode) throws LinkNotFoundException, IncorrectSecretCodeException {
        Link link = linkRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!link.getSecretCode().equals(secretCode)) {
            throw new IncorrectSecretCodeException("Incorrect secret code provided");
        }
        linkRepository.delete(link);
    }

    private String generateSecretCode() {
        Random random = new Random();
        StringBuilder secretCode = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            secretCode.append(digit);
        }

        return secretCode.toString();
    }
}
