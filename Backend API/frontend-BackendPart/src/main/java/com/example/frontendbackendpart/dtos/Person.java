package com.example.frontendbackendpart.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
    private String name;
    private String title;


    public Person(String name, String title) {
        this.name = name;
        this.title = title;
    }

    @Override
    @JsonProperty("welcome_message")
    public String toString() {
        return "Oh, hi there " + name + ", my dear " + title + "!";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
