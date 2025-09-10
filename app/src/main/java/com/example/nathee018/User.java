package com.example.nathee018;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String email;

    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void information() {
        System.out.println("\nUser");
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("email: " + email);
    }

    public void logout() {
        System.out.println("Log out");
    }
}