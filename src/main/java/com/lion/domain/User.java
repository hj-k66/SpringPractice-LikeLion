package com.lion.domain;

public class User {
    private String name;
    private String id;
    private String password;

    public User() {
    }

    public User(String id, String name, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
