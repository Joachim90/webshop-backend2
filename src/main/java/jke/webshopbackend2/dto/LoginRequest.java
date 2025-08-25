package jke.webshopbackend2.dto;

public record LoginRequest(
        String username,
        String rawPassword
) {
}
