package com.example.orientationexampleexam.DTOS;

public class LinkDTO {

    private Long id;
    private String url;
    private String alias;
    private int hitCount;

    // Constructors, getters, and setters

    // Default constructor
    public LinkDTO() {
    }

    // Parameterized constructor
    public LinkDTO(Long id, String url, String alias, int hitCount) {
        this.id = id;
        this.url = url;
        this.alias = alias;
        this.hitCount = hitCount;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }
}
