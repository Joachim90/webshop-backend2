package jke.webshopbackend2.security;

import jke.webshopbackend2.model.Customer;
import jke.webshopbackend2.model.Role;
import jke.webshopbackend2.repository.CustomerRepository;
import jke.webshopbackend2.repository.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Configuration
public class OAuth2Service {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    public static final String GITHUB_USER_PREFIX = "GITHUB:";

    public OAuth2Service(CustomerRepository customerRepository, RoleRepository roleRepository) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return request -> {
            OAuth2User oauth2User = delegate.loadUser(request);
            String username = oauth2User.getAttributes().get("login").toString();

            //kolla om användaren är registrerad
            Optional<Customer> existing = customerRepository.findByUsername(GITHUB_USER_PREFIX + username);
            //annars registrera
            Customer customer;
            Role userRole = roleRepository.findByName("ROLE_USER");
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (existing.isEmpty()) {
                customer = new Customer(GITHUB_USER_PREFIX + username);
                customer.addRole(userRole);
                customerRepository.save(customer);
            } else {
                customer = existing.get();
            }

            if (username.equals("is-fo")
            || username.equals("j-ekstedt")
            || username.equals("Joachim90")) {
                customer.addRole(adminRole);
            }

            return new ConcreteUserDetails(customer, oauth2User.getAttributes());
        };
    }
}
