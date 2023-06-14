package com.example.peertopeerchatapplication.controllers;

import com.example.peertopeerchatapplication.models.*;
import com.example.peertopeerchatapplication.services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AppController {
    private final MessageServices messageServices;

    @Value("#{environment.CHAT_APP_PEER_ADDRESS}")
    String[] ipsAsArray;

    WebClient web = WebClient.builder().build();

    @Autowired
    public AppController(MessageServices messageServices) {
        this.messageServices = messageServices;
    }

    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        // Create list of all messages and return the list
        List<Message> messages = messageServices.getAll();
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessageAPI(@RequestBody Message message) {
        // Create variable
        MessageRequest messageRequest = messageServices.createNewMessage(message);

        // Send the message to one peer
        web.post()
                .uri("http://" + ipsAsArray[0] + ":8080/api/message/receive")
                .bodyValue(messageRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // IP:
        // 0 - Mira
        // 1 - Max
        // 2 - Petr
        // 3 - Karel
        // 4 - Vašek

        // Send the message to every peer
//        for (String ipAddress : ipsAsArray) {
//            String uri = "http://" + ipAddress + ":8080/api/message/receive";
//
//            web.post()
//                    .uri(uri)
//                    .bodyValue(messageRequest)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//        }
        return ResponseEntity.ok().body(messageRequest);
    }

    @PostMapping("/api/message/receive")
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageRequest request) {
        // Extract the message and client from the request body
        Message message = request.getMessage();
        Client client = request.getClient();

        // Validate the presence of required fields
        if (message == null || message.getTimestamp() == null || client == null || client.getId() == null) {
            List<String> missingFields = new ArrayList<>();
            if (message == null || message.getTimestamp() == null) {
                missingFields.add("message.timestamp");
            }
            if (client == null || client.getId() == null) {
                missingFields.add("client.id");
            }

            // Respond with a 401 status and error message for missing fields
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Error ", "Missing field(s): " + String.join(", ", missingFields)));
        }

        // Save the message into the database
        messageServices.createNewMessage(message);

        // Respond with a 200 status and success message
        return ResponseEntity.ok(new SuccessResponse("ok"));
    }
}
