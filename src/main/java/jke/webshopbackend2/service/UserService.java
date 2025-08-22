package jke.webshopbackend2.service;

import jke.webshopbackend2.dto.LoginRequest;
import jke.webshopbackend2.dto.RegisterRequest;
import jke.webshopbackend2.dto.UserDto;
import jke.webshopbackend2.model.User;
import jke.webshopbackend2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.name()) != null) {
            return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);
        }
        User user = new User(
                registerRequest.name(),
                passwordEncoder.encode(registerRequest.rawPassword()),
                registerRequest.requestedRole()
        );
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<UserDto> login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.name());
        if (user != null && passwordEncoder.matches(loginRequest.rawPassword(), user.getPasswordHash())) {
            UserDto userDto = new UserDto(user.getUsername(), user.getRole());
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
