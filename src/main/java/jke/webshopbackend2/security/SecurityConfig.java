package jke.webshopbackend2.security;

import jke.webshopbackend2.repository.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //https://docs.google.com/presentation/d/1EiQ8KH4KPITJH6i2LXPWjzoenZUAO7r6/edit?slide=id.p9#slide=id.p9
   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/script/**").permitAll()
                        .requestMatchers("/register", "/login","/purchase").permitAll()
                        .requestMatchers("/home").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/delete-order").permitAll()
                        //.requestMatchers("/home").hasAnyRole()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                *//*.formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )*//*
                .logout(LogoutConfigurer::permitAll)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }*/


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/script/**").permitAll()
                        .requestMatchers("/register", "/login", "/purchase").permitAll()
                        .requestMatchers("/home").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/delete-order").permitAll()
                        //.requestMatchers("/home").hasAnyRole()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

   /* @Bean
    public UserDetailsService userDetailsService(CustomerRepository customerRepository) {
        return username -> customerRepository.findByUsername(username)
                .map(customer -> User.builder()
                        .username(customer.getUsername())
                        .password(customer.getPasswordHash())
                        .authorities(
                                customer.getRoles().stream()
                                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                                        .toArray(SimpleGrantedAuthority[]::new)
                        )
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }*/


}
