package com.example.plusproject.domain.accommodation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccommodationUpdateRequestDto {

    private String accommodationName;
    private String address;
    private String city;
    private String description;
    private String roomType;
    private String image;
    private String services;
    private Double price;
}

// @JsonInclude(JsonInclude.Include.NON_NULL) : JSON 직렬화 시 null 값은 제외됨 (불필요한 필드 제거)
