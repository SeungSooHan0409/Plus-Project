package com.example.plusproject.domain.user.entity;

import com.example.plusproject.domain.user.dto.SignUpRequestDto;
import com.example.plusproject.domain.user.dto.AuthResponseDto;
import com.example.plusproject.domain.user.enums.UserRole;

public class UserMapper {

    // Dto -> Entity
    public static User signUpRequestToUser (SignUpRequestDto dto, String encodedPassword){
        return User.builder()
                .name(dto.getName())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(encodedPassword)
                .phoneNumber(dto.getPhoneNumber())
                .residence(dto.getResidence())
                .role(UserRole.GUEST)
                .build();
    }

    // ResponseBody data (유저 정보) (Entity -> Dto)
    public static AuthResponseDto data(User user){
        return new AuthResponseDto(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
