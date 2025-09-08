package jke.webshopbackend2.dto;

import jakarta.validation.constraints.Pattern; //org.hibernate.annotations.processing.Pattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRequest(
        @NotBlank(message = "Ange ett användarnamn.")
        @Pattern(regexp = "^[a-zA-ZåÅäÄöÖ '\\-]+$", message = "Ogiltigt namn.")
        String username,
        @Size(min = 6, message = "Lösenordet måste innehålla minst 6 tecken.")
        String password,
        Set<String> requestedRoles
) {
}
