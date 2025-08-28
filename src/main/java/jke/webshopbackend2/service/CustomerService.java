package jke.webshopbackend2.service;

import jke.webshopbackend2.dto.LoginRequest;
import jke.webshopbackend2.dto.RegisterRequest;
import jke.webshopbackend2.model.Customer;
import jke.webshopbackend2.model.Role;
import jke.webshopbackend2.repository.RoleRepository;
import jke.webshopbackend2.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {

        final var existing = customerRepository.findByUsername(registerRequest.username());
        if (existing.isPresent()) {
            return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);
        }

        Customer customer = new Customer();
        customer.setUsername(registerRequest.username());
        customer.setPasswordHash(passwordEncoder.encode(registerRequest.rawPassword()));
        if (registerRequest.requestedRoles() == null || registerRequest.requestedRoles().isEmpty()) {
            customer.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        } else {
            Set<Role> roles = registerRequest.requestedRoles().stream()
                    .map(roleRepository::findByName)
                    .collect(Collectors.toSet());

            customer.setRoles(roles);
        }

        return ResponseEntity.ok().body(customerRepository.save(customer));
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        final var user = customerRepository.findByUsername(loginRequest.username());
        if (user.isPresent() && passwordEncoder.matches(loginRequest.rawPassword(), user.get().getPasswordHash())) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public Customer findUserByUsername(String username) {
        return customerRepository.findByUsername(username).orElse(null);
    }

}
