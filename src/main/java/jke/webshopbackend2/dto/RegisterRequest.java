package jke.webshopbackend2.dto;

import org.hibernate.annotations.processing.Pattern;

public record RegisterRequest(
        String name,
        String rawPassword
) {
}
