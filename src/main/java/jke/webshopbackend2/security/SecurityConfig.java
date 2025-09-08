package jke.webshopbackend2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    OAuth2Service oAuth2Service;

    public SecurityConfig(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/script/**").permitAll()
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/home").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/delete-order").hasRole("ADMIN")
                        .requestMatchers("/purchase").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .userInfoEndpoint(user -> user.userService(oAuth2Service.oauth2UserService()))
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                //.csrf(AbstractHttpConfigurer::disable)
        ;

        return http.build();
    }
}
