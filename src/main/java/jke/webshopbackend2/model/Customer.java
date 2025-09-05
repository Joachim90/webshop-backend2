package jke.webshopbackend2.model;

import jakarta.persistence.*;
import jke.webshopbackend2.security.OAuth2Service;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String passwordHash;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_roles",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Customer(String name, String encode, Set<Role> roles) {
        this.username = name;
        this.passwordHash = encode;
        this.roles = roles;
    }

    public Customer(String name, String passwordHash) {
        this.username = name;
        this.passwordHash = passwordHash;
    }

    public Customer(String name) {
        this.username = name;
    }

    public Customer() {}

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Set<Role> getRoles() {
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void addRole(String role) {
        this.roles.add(new Role(role));
    }
}

