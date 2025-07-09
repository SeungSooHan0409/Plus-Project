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
    private final String accommodationAddress;




    public RequestDto(Long guestCount, LocalDate checkInDate, String accommodationAddress) {
        this.guestCount = guestCount;
        this.checkInDate = checkInDate;
        this.accommodationAddress =accommodationAddress;
    }
}
