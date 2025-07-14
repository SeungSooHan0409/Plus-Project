package com.example.plusproject.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReservationData {

    private Long id;
    private String accommodationAddress;
    private Long guestCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate checkOutDate;


    public ReservationData(Long id, String accommodationAddress, Long guestCount, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = id;
        this.accommodationAddress = accommodationAddress;
        this.guestCount = guestCount;
        this.checkInDate = checkInDate;
        this. checkOutDate = checkOutDate;
    }
}
