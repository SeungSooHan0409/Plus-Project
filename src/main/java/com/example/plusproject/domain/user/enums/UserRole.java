package com.example.plusproject.domain.user.enums;

import com.example.plusproject.common.exception.InvalidRequestException;

import java.util.Arrays;

public enum UserRole {
    GUEST, HOST;

    public static UserRole of(String role){
        return Arrays.stream(UserRole.values())
                .filter(r->r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 UserRole"));
    }
}
