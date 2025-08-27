package jke.webshopbackend2.service;

import jke.webshopbackend2.dto.LoginRequest;
import jke.webshopbackend2.dto.RegisterRequest;
import jke.webshopbackend2.model.User;
import jke.webshopbackend2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {

        final var existing = userRepository.findByUsername(registerRequest.username());
        if (existing.isPresent()) {
            return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(registerRequest.username());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.rawPassword()));
        if (registerRequest.requestedRoles() == null || registerRequest.requestedRoles().isEmpty()) {
            user.setRoles(List.of("ROLE_USER"));
        } else {
            user.setRoles(registerRequest.requestedRoles());
        }

        return ResponseEntity.ok().body(userRepository.save(user));
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        final var user = userRepository.findByUsername(loginRequest.username());
        if (user.isPresent() && passwordEncoder.matches(loginRequest.rawPassword(), user.get().getPasswordHash())) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
