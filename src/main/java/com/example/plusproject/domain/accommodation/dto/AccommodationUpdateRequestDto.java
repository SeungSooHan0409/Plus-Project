package com.example.plusproject.domain.accommodation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccommodationUpdateRequestDto {

    private String accommodationName;
    private String address;
    private String city;
    private String description;
    private String roomType;
    private String image;
    private String services;
    private Double price;

    // 유효성 검증 메소드
    public boolean hasValidAccommodationName() {
        return accommodationName != null && !accommodationName.isBlank();
    }

    public boolean hasValidAddress() {
        return address != null && !address.isBlank();
    }

    public boolean hasValidCity() {
        return city != null && !city.isBlank();
    }

    public boolean hasValidDescription() {
        return description != null && !description.isBlank();
    }

    public boolean hasValidRoomType() {
        return roomType != null && !roomType.isBlank();
    }

    public boolean hasValidImage() {
        return image != null && !image.isBlank();
    }

    public boolean hasValidServices() {
        return services != null && !services.isBlank();
    }

    public boolean hasValidPrice() {
        return price != null && price > 0;
    }
}