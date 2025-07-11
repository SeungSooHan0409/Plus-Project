package com.example.plusproject.domain.accommodation.dto;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// 생성자 dto를 통해서 불러오기 위해 필요
@NoArgsConstructor
@AllArgsConstructor
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

    public static AccommodationCreateResponseDto from(Accommodation entity) {
        return new AccommodationCreateResponseDto(
                entity.getId(),
                entity.getAccommodationName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getDescription(),
                entity.getRoomType(),
                entity.getImage(),
                entity.getServices(),
                entity.getPrice(),
                entity.getUser().getId() // hostId
        );
    }
}
