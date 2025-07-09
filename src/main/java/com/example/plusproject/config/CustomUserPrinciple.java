package com.example.plusproject.config;

import com.example.plusproject.domain.user.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Spring Security는 내부적으로 권한 검사를 getAuthorities()를 통해 합니다. 하지만 UserDetails를 상속받지 않고 있고, GrantedAuthority도 제공하지 않고 있습니다.

@Getter
public class CustomUserPrinciple implements UserDetails {

    private final Long id;
    private final String nickname;
    private final UserRole userRole;

    public CustomUserPrinciple(Long id, String nickname, UserRole userRole){
        this.id = id;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
