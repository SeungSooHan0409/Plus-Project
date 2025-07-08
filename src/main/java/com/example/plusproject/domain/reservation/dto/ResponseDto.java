package com.example.plusproject.domain.reservation.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseDto {

    private boolean success;
    private String message;
    private ReservationData data;
    private LocalDateTime timestamp;

    public ResponseDto(boolean success, String message, ReservationData data, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
}
