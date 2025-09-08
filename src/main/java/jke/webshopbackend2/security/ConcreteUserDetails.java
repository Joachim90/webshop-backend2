package jke.webshopbackend2.security;

import jke.webshopbackend2.model.Customer;
import jke.webshopbackend2.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ConcreteUserDetails implements UserDetails, OAuth2User {
    private final Customer customer;
    private final Map<String, Object> attributes;

    public ConcreteUserDetails(Customer customer) {
        this(customer, Map.of());
    }

    public ConcreteUserDetails(Customer customer, Map<String, Object> attributes) {
        this.customer = customer;
        this.attributes = attributes != null ? attributes : Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : customer.getRoles()){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            authorities.add(authority);
        }
        return authorities;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String getPassword() {
        return customer.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return customer.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return customer.getUsername();
    }
}
