package com.example.linterna.entities;

public class Event {
    private Env env;
    private TypeEvent typeEvents;
    private String state;
    private String description;
    private Integer group;

    public Env getEnv() {
        return env;
    }

    public TypeEvent getTypeEvents() {
        return typeEvents;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }
}
