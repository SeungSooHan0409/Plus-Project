package com.example.plusproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiResponseDto {

    private boolean success;
    private String message;
    private Object data;
    private LocalDateTime timestamp;

    // 성공 응답 static 메서드
    public static ApiResponseDto success(String message,Object date){
        return new ApiResponseDto(true, message , date, LocalDateTime.now());
    }

    // 실패 응답 static 메서드
    public static ApiResponseDto error(String message){
        return new ApiResponseDto(false, message, null, LocalDateTime.now());
    }
}
