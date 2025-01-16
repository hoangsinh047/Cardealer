package com.hdsinh.cardealer.security;

import java.util.Collection;

import com.hdsinh.cardealer.entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private Users user;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails() {

    }

    public CustomUserDetails(Users user, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isCredentialNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
