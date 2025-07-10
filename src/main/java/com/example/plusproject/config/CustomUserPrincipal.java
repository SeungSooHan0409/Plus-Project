package com.example.plusproject.config;

import com.example.plusproject.domain.user.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserPrincipal implements UserDetails {

    private final Long id;
    private final String nickname;
    private final UserRole userRole;

    public CustomUserPrincipal(Long id, String nickname, UserRole userRole){
        this.id = id;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()->getUserRole().name());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return nickname;
    }
}
