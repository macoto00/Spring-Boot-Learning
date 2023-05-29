package com.example.theredditproject.exceptions;

import org.springframework.http.HttpStatus;

public class PostException extends RuntimeException {
    private HttpStatus httpStatus;

    public PostException(String message, HttpStatus statusCode) {
        super(message);
        this.httpStatus = statusCode;
    }

    public HttpStatus getStatusCode() {
        return httpStatus;
    }
}
