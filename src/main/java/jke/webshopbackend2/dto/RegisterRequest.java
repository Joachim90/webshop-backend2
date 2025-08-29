package jke.webshopbackend2.dto;

import jke.webshopbackend2.model.Role;

import java.util.Set;

public record RegisterRequest(
        String username,
        String password,
        Set<String> requestedRoles
) {
}
