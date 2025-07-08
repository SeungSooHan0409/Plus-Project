package com.example.plusproject.domain.reservation.dto;

import lombok.Getter;

@Getter
public class GuestChangeRequestDto {

    private Long guestCount;


    public GuestChangeRequestDto(Long guestCount) {
        this.guestCount = guestCount;
    }
}
