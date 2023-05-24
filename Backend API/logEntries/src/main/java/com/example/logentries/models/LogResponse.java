package com.example.logentries.models;

import java.util.List;

public class LogResponse {
    private List<LogEntry> entries;
    private int entryCount;

    public LogResponse(List<LogEntry> entries, int entryCount) {
        this.entries = entries;
        this.entryCount = entryCount;
    }

    public List<LogEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LogEntry> entries) {
        this.entries = entries;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }
}

