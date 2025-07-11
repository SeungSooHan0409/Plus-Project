package com.example.plusproject.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequestDto {

    @NotNull
    @Min(0)
    @Max(5)
    private double rating;

    @NotBlank
    private String content;

    private String imageUrl;

    @NotNull
    private Long reservationId;

}
