package jke.webshopbackend2.dto;

public record RegisterRequest(
        String name,
        String rawPassword,
        String requestedRole
) {
}
