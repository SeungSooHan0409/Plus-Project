package com.example.plusproject.domain.trending.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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

		// 지난 12시간 키 수집
		List<String> keys = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			LocalDateTime dateTime = LocalDateTime.now().minusHours(i);
			String key = "search:" + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:00"));

			// Redis에 존재하는 키만 추가
			if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
				keys.add(key);
			}
		}

		// 키들을 합쳐서 unionKey 생성
		Set<String> topWords = Collections.emptySet();;
		if (keys.size() >= 2) {
			String unionKey = "search:combined:"
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:00")); // 12시간 합친 데이터의 새 키

			// 만들어둔 합계 순위 표가 없다면 생성
			if (Boolean.FALSE.equals(redisTemplate.hasKey(unionKey))) {
				redisTemplate.opsForZSet().unionAndStore(keys.get(0), keys.subList(1, keys.size()), unionKey); //((기준키, 나머지키, 새로운키)
				redisTemplate.expire(unionKey, Duration.ofHours(1)); // 만료 시간 : 1시간
			}

			// 상위 10개 검색어 조회
			topWords = redisTemplate.opsForZSet().reverseRange(unionKey, 0, 9);
		}
		else if (keys.size() == 1) {
			topWords = redisTemplate.opsForZSet().reverseRange(keys.get(0), 0, 9);
		}


		// 응답용 DTO로 변환
		List<TrendingResponseDto> trendingList = new ArrayList<>();
		int rank = 1;
		for (String word : topWords) {
			trendingList.add(new TrendingResponseDto(rank++, word));
		}

		return trendingList;
	}
}
