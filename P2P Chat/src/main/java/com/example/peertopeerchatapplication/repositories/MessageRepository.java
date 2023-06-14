package com.example.peertopeerchatapplication.repositories;

import com.example.peertopeerchatapplication.models.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
