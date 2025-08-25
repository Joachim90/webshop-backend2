package jke.webshopbackend2.dto;

import java.util.List;

public record UserDto(
        String username,
        List<String> roles
) {
}
