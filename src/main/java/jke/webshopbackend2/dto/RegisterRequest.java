package jke.webshopbackend2.dto;

import java.util.Set;

public record RegisterRequest(
        String username,
        String password,
        Set<String> requestedRoles
) {
}
