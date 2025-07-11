package com.example.plusproject.domain.trending;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class TrendAspect {

	private final RedisTemplate<String, String> redisTemplate; // RedisTemplate을 사용하여 Redis와 통신

	@AfterReturning("execution(* com.example.plusproject..AccommodationController.searchAccommodationsV1(..))")
	public void searchLogging(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();

		if (args.length > 0 && args[0] instanceof String keyword && !keyword.isBlank()) {
			String[] words = keyword.split("\\s+"); // 공백 기준 분리

			// 집계 : 1시간 동안
			String key = "search:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:00"));

			// 단어 검색횟수 기준 정렬 저장
			for (String word : words) {
				redisTemplate.opsForZSet().incrementScore(key, word, 1);
			}

			// TTL 만료 시간 : 7일
			if (redisTemplate.getExpire(key) == -1) { // -1이면 TTL 없음 (무제한)
				redisTemplate.expire(key, Duration.ofDays(7));
			}
		}
	}
}
