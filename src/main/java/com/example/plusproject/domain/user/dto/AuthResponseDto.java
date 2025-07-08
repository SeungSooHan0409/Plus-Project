package com.example.plusproject.domain.user.dto;

import com.example.plusproject.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuthResponseDto {
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private UserRole userRole;
    private LocalDateTime createdAt;
}