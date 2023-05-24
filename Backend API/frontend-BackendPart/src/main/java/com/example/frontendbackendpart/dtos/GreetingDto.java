package com.example.frontendbackendpart.dtos;

public class GreetingDto {
    private String welcome_message;

    public GreetingDto(String welcome_message) {
        this.welcome_message = welcome_message;
    }

    public String getWelcome_message() {
        return welcome_message;
    }

    public void setWelcome_message(String welcome_message) {
        this.welcome_message = welcome_message;
    }
}

