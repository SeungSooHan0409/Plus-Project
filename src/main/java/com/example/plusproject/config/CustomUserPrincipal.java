package com.example.plusproject.config;

import com.example.plusproject.domain.user.enums.UserRole;
import lombok.Getter;

@Getter
public class CustomUserPrincipal {

    private final Long id;
    private final String email;
    private final UserRole userRole;

    public CustomUserPrincipal(Long id, String email, UserRole userRole){
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }
}
