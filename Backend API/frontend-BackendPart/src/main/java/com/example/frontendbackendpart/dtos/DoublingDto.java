package com.example.frontendbackendpart.dtos;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class DoublingDto {
    private int received;
    private int result;

    public DoublingDto(int received, int result) {
        this.received = received;
        this.result = result;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
