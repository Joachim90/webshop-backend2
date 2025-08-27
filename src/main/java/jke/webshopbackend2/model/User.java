package jke.webshopbackend2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String passwordHash;

    private List<String> roles = List.of("ROLE_USER", "ROLE_ADMIN");

    public User(String name, String encode, List<String> roles) {
        this.username = name;
        this.passwordHash = encode;
        this.roles = roles;
    }

    public User(String name, String passwordHash) {
        this.username = name;
        this.passwordHash = passwordHash;
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

