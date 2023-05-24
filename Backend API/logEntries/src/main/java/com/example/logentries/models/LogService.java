package com.example.logentries.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {
    private LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void saveLogEntry(String endpoint, String data) {
        LogEntry logEntry = new LogEntry(LocalDateTime.now(), endpoint, data);
        logRepository.save(logEntry);
    }
}

