package com.techthrivecatalyst.expensafe.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static java.util.UUID.randomUUID;

@Entity
@Table(name = "app_user")
public class AppUserEntity {
    @Id
    private String id;

    @Column(unique = true)
    private String username;

    private String password;

    private AppUserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AppUserEntity() {
    }

    public static AppUserEntity create(String username, String password) {
        AppUserEntity user = new AppUserEntity(username, password);
        user.id = randomUUID().toString();
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
