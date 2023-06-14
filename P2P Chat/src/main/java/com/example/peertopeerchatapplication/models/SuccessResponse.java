package com.example.peertopeerchatapplication.models;

public class SuccessResponse {
    private String status;

    public SuccessResponse(String message) {
        this.status = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
