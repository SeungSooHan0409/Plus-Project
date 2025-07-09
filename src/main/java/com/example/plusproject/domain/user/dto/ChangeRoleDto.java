package com.example.plusproject.domain.user.dto;

import com.example.plusproject.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeRoleDto {
    private UserRole role;
}
