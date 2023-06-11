package com.example.orientationexampleexam.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Link {

    @Id
    @GeneratedValue
    private Long id;
    private String url;

    private String alias;

    private int hitCount;

    private String secretCode;

    public Link() {
    }

    public Link(String url, String alias, int hitCount, String secretCode) {
        this.url = url;
        this.alias = alias;
        this.hitCount = hitCount;
        this.secretCode = secretCode;
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

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

