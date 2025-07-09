package com.example.plusproject.domain.trending.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.plusproject.domain.trending.dto.TrendingResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrendingService {

	private final RedisTemplate<String, String> redisTemplate;

	public List<TrendingResponseDto> getTrending() {
		Set<String> keywords = redisTemplate.opsForZSet().reverseRange("search", 0, 9);

		List<TrendingResponseDto> trendingList = new ArrayList<>();
		int rank = 1;
		for (String keyword : keywords) {
			trendingList.add(new TrendingResponseDto(rank++, keyword));
		}

		return trendingList;
	}
}
