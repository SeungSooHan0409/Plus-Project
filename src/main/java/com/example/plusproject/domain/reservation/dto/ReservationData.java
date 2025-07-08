package com.example.plusproject.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReservationData {

    private Long id;
    private String accommodationName;
    private Long guestCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;


    public ReservationData(Long id, String accommodationName, Long guestCount, LocalDate checkInDate) {
        this.id = id;
        this.accommodationName = accommodationName;
        this.guestCount = guestCount;
        this.checkInDate = checkInDate;
    }
}
