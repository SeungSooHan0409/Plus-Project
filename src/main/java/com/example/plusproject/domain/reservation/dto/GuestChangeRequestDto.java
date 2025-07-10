package com.example.plusproject.domain.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GuestChangeRequestDto {

    private final Long guestCount;

    @NotNull
    public GuestChangeRequestDto(Long guestCount) {
        this.guestCount = guestCount;
    }
}
