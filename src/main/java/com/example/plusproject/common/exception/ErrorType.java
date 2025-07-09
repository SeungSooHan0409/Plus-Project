package com.example.plusproject.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    // reservation 에러
    RESVERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String errorMessage;


    ErrorType(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
