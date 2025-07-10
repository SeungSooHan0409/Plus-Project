package com.example.plusproject.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    // Reservation
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다."),
    INVALID_GUESTCOUNT(HttpStatus.BAD_REQUEST,"1 이상의 숫자만 입력가능 합니다."),
    ASSIGNED_DATE(HttpStatus.BAD_REQUEST, "해당 날짜에 이미 예약이 존재합니다."),

    // User
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    NONEXISTENT_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    SAME_ROLE(HttpStatus.BAD_REQUEST, "다른 역할을 입력해주세요."),
    NO_ACCESS(HttpStatus.BAD_REQUEST, "접근 권한이 없습니다"),

    //Accommodation
    INVALID_USER(HttpStatus.BAD_REQUEST, "호스트가 아닙니다."),
    NONEXISTENT_ACCOMMODATION(HttpStatus.BAD_REQUEST, "존재하지 않는 숙소입니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorType(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
