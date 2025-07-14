package com.example.plusproject.domain.accommodation.dto;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccommodationUpdateResponseDto {

    private Long id;
    private String accommodationName;
    private String address;
    private String city;
    private Integer capacity;
    private String description;
    private String roomType;
    private String image;
    private String services;
    private Double price;
    private Long hostId;

    public static AccommodationUpdateResponseDto from(Accommodation entity) {
        return new AccommodationUpdateResponseDto(
                entity.getId(),
                entity.getAccommodationName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getCapacity(),
                entity.getDescription(),
                entity.getRoomType(),
                entity.getImage(),
                entity.getServices(),
                entity.getPrice(),
                entity.getUser().getId()
        );
    }
}