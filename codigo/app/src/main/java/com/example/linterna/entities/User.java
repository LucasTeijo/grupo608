package com.example.linterna.entities;

public class User {
    private Env env;
    private String name;
    private String lastname;
    private Integer dni;
    private String email;
    private String password;
    private Integer commission;
    private Integer group;

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getCommission() {
        return commission;
    }

    public Integer getGroup() {
        return group;
    }

    public User setEnv(Env env) {
        this.env = env;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public User setDni(Integer dni) {
        this.dni = dni;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setCommission(Integer commission) {
        this.commission = commission;
        return this;
    }

    public User setGroup(Integer group) {
        this.group = group;
        return this;
    }
}
