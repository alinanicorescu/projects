package com.voya.example.accountmanager.model;

import java.util.Date;

/**
 * Created by alinanicorescu on 17/12/2016.
 */
public class UserDetails {

    private String email;
    private String name;
    private String username;

    public UserDetails() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
