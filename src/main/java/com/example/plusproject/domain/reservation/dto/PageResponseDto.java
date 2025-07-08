package com.example.plusproject.domain.reservation.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
public class PageResponseDto {

    private boolean success;
    private String message;
    private Page<ReservationData> data;
    private LocalDateTime timestamp;


    public PageResponseDto(boolean success, String message, Page<ReservationData> data, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
}
