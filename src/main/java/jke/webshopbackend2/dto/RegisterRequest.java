package jke.webshopbackend2.dto;

import java.util.List;

public record RegisterRequest(
        String name,
        String rawPassword,
        List<String> requestedRoles
) {
}
