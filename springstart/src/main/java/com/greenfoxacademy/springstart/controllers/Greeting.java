package com.greenfoxacademy.springstart.controllers;

import java.util.concurrent.atomic.AtomicLong;

public class Greeting {
    private long greetCount;
    private String contentFields;

    public Greeting(long greetCount, String contentFields) {
        this.greetCount = greetCount;
        this.contentFields = contentFields;
    }

    public long getGreetCount() {
        return greetCount;
    }

    public String getContentFields() {
        return contentFields;
    }
}
