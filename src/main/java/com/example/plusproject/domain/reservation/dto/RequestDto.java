package com.example.plusproject.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RequestDto {

    private final Long guestCount;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate checkInDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate checkOutDate;

    @NotNull
    private final String accommodationAddress;




    public RequestDto(Long guestCount, LocalDate checkInDate, LocalDate checkOutDate, String accommodationAddress) {
        this.guestCount = guestCount;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.accommodationAddress =accommodationAddress;
    }
}
