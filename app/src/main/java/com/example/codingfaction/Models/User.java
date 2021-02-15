package com.example.codingfaction.Models;

public class User {

    private String id;
    private String email;
    private String access;

    public User() {
    }

    public User(String id, String email, String access) {
        this.id = id;
        this.email = email;
        this.access = access;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
