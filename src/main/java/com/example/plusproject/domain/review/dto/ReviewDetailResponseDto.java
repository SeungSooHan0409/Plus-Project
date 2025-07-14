package com.example.plusproject.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailResponseDto {

    private Long reviewId;
    private UserDto userDto;
    private AccommodationDto accommodationDto;
    private Double rating;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

}
