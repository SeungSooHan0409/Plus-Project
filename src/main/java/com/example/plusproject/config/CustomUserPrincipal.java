package com.example.plusproject.config;

import com.example.plusproject.domain.user.enums.UserRole;
import lombok.Getter;

@Getter
public class CustomUserPrincipal {

    private final Long id;
    private final String nickname;
    private final UserRole userRole;

    public CustomUserPrincipal(Long id, String nickname, UserRole userRole){
        this.id = id;
        this.nickname = nickname;
        this.userRole = userRole;
    }
}
