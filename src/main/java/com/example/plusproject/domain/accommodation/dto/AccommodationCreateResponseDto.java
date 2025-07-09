package com.example.plusproject.domain.accommodation.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AccommodationCreateResponseDto {
    private Long id;
    private String accommodationName;
    private String address;
    private String city;
    private String description;
    private String roomType;
    private String image;
    private String services;
    private Double price;
    private Long hostId;

    public AccommodationCreateResponseDto(Long id, String accommodationName, String address, String city, String description, String roomType, String image, String services, Double price, Long hostId) {
        this.id = id;
        this.accommodationName = accommodationName;
        this.address = address;
        this.city = city;
        this.description = description;
        this.roomType = roomType;
        this.image = image;
        this.services = services;
        this.price = price;
        this.hostId = hostId;
    }
}
