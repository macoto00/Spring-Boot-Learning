package com.example.logentries.controllers;

import com.example.logentries.models.LogEntry;
import com.example.logentries.models.LogRepository;
import com.example.logentries.models.LogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class LogController {
    private LogRepository logRepository;

    @Autowired
    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping("/log")
    public LogResponse getAllLogEntries() {
        Iterable<LogEntry> entries = logRepository.findAll();
        int entryCount = ((Collection<?>) entries).size();
        return new LogResponse((List<LogEntry>) entries, entryCount);
    }
}

