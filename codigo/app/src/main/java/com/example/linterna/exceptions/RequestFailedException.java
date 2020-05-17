package com.example.linterna.exceptions;

public class RequestFailedException extends RuntimeException {

    private String userMessage;

    public RequestFailedException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    public RequestFailedException(String message, String userMessage, Throwable cause) {
        super(message, cause);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
