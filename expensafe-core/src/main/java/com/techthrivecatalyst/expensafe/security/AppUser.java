package com.techthrivecatalyst.expensafe.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    private String id;

    @Column(unique = true)
    private String username;

    private String password;

    private AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AppUser() {
    }

    public static AppUser create(String username, String password) {
        AppUser user = new AppUser(username, password);
        user.id = java.util.UUID.randomUUID().toString();
        return user;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
