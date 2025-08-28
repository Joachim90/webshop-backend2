package jke.webshopbackend2.dto;

import jke.webshopbackend2.model.Role;

import java.util.Set;

public record UserDto(
        String username,
        Set<Role> roles
) {
}
