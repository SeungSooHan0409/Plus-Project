package com.example.plusproject.common.exception;

import com.example.plusproject.common.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseDto> handleCustomException(CustomException e) {

        return ResponseEntity
                .status(e.getErrorType().getHttpStatus())
                .body(ApiResponseDto.error(e.getErrorType().getErrorMessage()));

    }

}
