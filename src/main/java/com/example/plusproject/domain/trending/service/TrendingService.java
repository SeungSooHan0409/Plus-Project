package com.example.plusproject.domain.trending.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		String key = "search:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:00"));

		Set<String> keywords = redisTemplate.opsForZSet().reverseRange(key, 0, 9);

		List<TrendingResponseDto> trendingList = new ArrayList<>();
		int rank = 1;
		for (String keyword : keywords) {
			trendingList.add(new TrendingResponseDto(rank++, keyword));
		}

		return trendingList;
	}
}
