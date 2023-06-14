package com.example.peertopeerchatapplication.models;

public class MessageRequest {
    private Message message;
    private Client client;

    public MessageRequest() {
    }

    public MessageRequest(Message message, Client client) {
        this.message = message;
        this.client = client;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
