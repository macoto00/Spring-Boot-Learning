package com.example.orientationexampleexam.models;

import com.example.orientationexampleexam.DTOS.LinkDTO;

import java.util.List;
import java.util.stream.Collectors;

public class LinkMapper {

    public static LinkDTO mapToDTO(Link link) {
        LinkDTO linkDTO = new LinkDTO();
        linkDTO.setId(link.getId());
        linkDTO.setUrl(link.getUrl());
        linkDTO.setAlias(link.getAlias());
        linkDTO.setHitCount(link.getHitCount());
        return linkDTO;
    }

    public static List<LinkDTO> mapToDTOList(List<Link> links) {
        return links.stream()
                .map(LinkMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public static Link mapToEntity(LinkDTO linkDTO, String secretCode) {
        Link link = new Link();
        link.setUrl(linkDTO.getUrl());
        link.setAlias(linkDTO.getAlias());
        link.setHitCount(linkDTO.getHitCount());
        link.setSecretCode(secretCode);
        return link;
    }
}
