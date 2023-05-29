package com.example.theredditproject.exceptions;

public class UserException extends RuntimeException {
    private String message;

    public UserException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
