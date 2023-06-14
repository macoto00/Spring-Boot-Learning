package com.example.peertopeerchatapplication.services;

import com.example.peertopeerchatapplication.models.Client;
import com.example.peertopeerchatapplication.models.Message;
import com.example.peertopeerchatapplication.models.MessageRequest;
import com.example.peertopeerchatapplication.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServicesCRUD implements MessageServices {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServicesCRUD(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageRequest createNewMessage(Message message) {
        Client client = new Client();
        client.setId(message.getUsername());
        MessageRequest messageRequest = new MessageRequest(message, client);
        messageRepository.save(message);
        return messageRequest;
    }

    @Override
    public List<Message> getAll() {
        return (List<Message>) messageRepository.findAll();
    }
}
