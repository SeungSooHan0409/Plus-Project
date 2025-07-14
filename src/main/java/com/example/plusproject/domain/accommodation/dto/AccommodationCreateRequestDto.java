package com.example.plusproject.domain.accommodation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccommodationCreateRequestDto {

    @NotBlank(message = "숙소 이름은 필수 입력사항입니다.")
    private String accommodationName;

    @NotBlank(message = "숙소 주소는 필수 입력사항입니다.")
    private String address;

    @NotBlank(message = "숙소 도시명은 필수 입력사항입니다.")
    private String city;

    @NotNull(message = "최대 숙박인원은 필수 입력사항입니다.")
    @Positive(message = "인원수는 0보다 커야 합니다.")
    private Integer capacity;

    @NotBlank(message = "숙소 설명은 필수 입력사항입니다.")
    private String description;

    @NotBlank(message = "숙소의 방 형태는 필수 입력사항입니다.")
    private String roomType;

    @NotBlank(message = "숙소 이미지는 필수 입력사항입니다.")
    private String image;

    @NotBlank(message = "제공 서비스는 필수 입력사항입니다.")
    private String services;

    @NotNull(message = "숙소 가격은 필수 입력사항입니다.")
    @Positive(message = "숙소 가격은 0보다 커야 합니다.")
    private Double price; // 객체형으로 바꿔야 @NotNull 적용됨
}

