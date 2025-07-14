package com.example.plusproject.domain.accommodation.dto;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationSearchResponseDto {
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

    public static AccommodationSearchResponseDto from(Accommodation entity) {
        return new AccommodationSearchResponseDto(
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
                entity.getUser().getId() // hostId
        );
    }
}
