package com.example.logentries.models;

public interface LogRepository extends CrudRepository<LogEntry, Long> {
    // Případné metody pro manipulaci s logovacími záznamy

    LogEntry save(LogEntry logEntry);

    Iterable<LogEntry> findAll();

}


