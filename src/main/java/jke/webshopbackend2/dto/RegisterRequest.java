package jke.webshopbackend2.dto;

import java.util.List;

public record RegisterRequest(
        String name,
        String rawPassword,
        List<String> requestedRoles
) {
    @Override
    public String rawPassword() {
        return rawPassword;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<String> requestedRoles() {
        return requestedRoles;
    }
}
