package com.example.linterna.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Event {
    private Env env = Env.DEV;
    private TypeEvent typeEvents;
    private String state;
    private String description;
    private Integer group = 608;

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

    public Integer getGroup() {
        return group;
    }

    public Event setTypeEvents(TypeEvent typeEvents) {
        this.typeEvents = typeEvents;
        return this;
    }

    public Event setState(String state) {
        this.state = state;
        return this;
    }

    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return new EqualsBuilder()
                .append(env, event.env)
                .append(typeEvents, event.typeEvents)
                .append(state, event.state)
                .append(description, event.description)
                .append(group, event.group)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(env)
                .append(typeEvents)
                .append(state)
                .append(description)
                .append(group)
                .toHashCode();
    }
}
