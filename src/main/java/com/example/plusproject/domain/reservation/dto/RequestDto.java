package com.example.plusproject.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RequestDto {

    private final Long guestCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate checkInDate;

    private final String accommodationName;




    public RequestDto(Long guestCount, LocalDate checkInDate, String accommodationName) {
        this.guestCount = guestCount;
        this.checkInDate = checkInDate;
        this.accommodationName =accommodationName;
    }
}
