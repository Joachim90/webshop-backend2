package jke.webshopbackend2.dto;

import java.util.List;

public record RegisterRequest(
        String username,
        String rawPassword,
        List<String> requestedRoles
) {
}
