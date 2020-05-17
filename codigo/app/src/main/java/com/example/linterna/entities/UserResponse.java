package com.example.linterna.entities;

public class UserResponse {
    private String state;
    private Env env;
    private String token;
    private User user;

    public String getState() {
        return state;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
