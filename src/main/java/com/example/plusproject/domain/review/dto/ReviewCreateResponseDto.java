package com.example.plusproject.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateResponseDto {

    private Long reviewId;
    private String nickname;
    private String content;
    private LocalDateTime createAt;

}
