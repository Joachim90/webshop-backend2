package jke.webshopbackend2.dto;

public record LoginRequest(
        String name,
        String rawPassword
) {

}
