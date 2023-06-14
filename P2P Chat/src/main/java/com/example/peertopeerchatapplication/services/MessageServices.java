package com.example.peertopeerchatapplication.services;

import com.example.peertopeerchatapplication.models.Message;
import com.example.peertopeerchatapplication.models.MessageRequest;

import java.util.List;

public interface MessageServices {
    MessageRequest createNewMessage(Message message);
    List<Message> getAll();
}
