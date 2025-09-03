package jke.webshopbackend2.security;

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
import java.util.Set;


@Configuration
public class OAuth2Service {

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return request -> {
            OAuth2User oauth2User = delegate.loadUser(request);

            //kolla om användaren är registrerad

            //annars registrera

            //

            Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oauth2User.getAuthorities());
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            return new DefaultOAuth2User(mappedAuthorities, oauth2User.getAttributes(), "login");
        };
    }
}
