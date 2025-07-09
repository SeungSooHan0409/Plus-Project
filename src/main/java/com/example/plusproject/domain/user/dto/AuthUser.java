package com.example.plusproject.domain.user.dto;

import com.example.plusproject.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUser {
    private final Long id;
    private final String nickname;
    private final UserRole userRole;

}
