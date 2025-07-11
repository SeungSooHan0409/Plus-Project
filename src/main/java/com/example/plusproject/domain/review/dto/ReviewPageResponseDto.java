package com.example.plusproject.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewPageResponseDto<T> {

    private List<T> reviews;
    private PageDto page;
}
