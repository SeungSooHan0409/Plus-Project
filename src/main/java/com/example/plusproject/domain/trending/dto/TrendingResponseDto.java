package com.example.plusproject.domain.trending.dto;

import lombok.Getter;

@Getter
public class TrendingResponseDto {

	private int rank;
	private String keyword;

	public TrendingResponseDto(int rank, String keyword) {
		this.rank = rank;
		this.keyword = keyword;
	}
}
