package com.example.plusproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // User
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    NONEXISTENT_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    SAME_ROLE(HttpStatus.BAD_REQUEST, "다른 역할을 입력해주세요."),
    NO_ACCESS(HttpStatus.BAD_REQUEST, "접근 권한이 없습니다");


    private final HttpStatus status;
    private final String message;
}
