package com.example.plusproject.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageDto {

    private int currentPage;
    private int size;
    private int totalPages;
    private long totalElements;

}
