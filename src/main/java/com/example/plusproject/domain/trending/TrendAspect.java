package com.example.plusproject.domain.trending;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class TrendAspect {

	private final RedisTemplate<String, String> redisTemplate; // RedisTemplate을 사용하여 Redis와 통신

	@Before("execution(* com.example.plusproject..TrendingController.test(..))")
	public void searchLogging(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();

		if (args.length > 0 && args[0] instanceof String search && !search.isBlank()) {
			String[] keywords = search.split("\\s+"); // 공백 기준 분리

			for (String keyword : keywords) {
				redisTemplate.opsForZSet().incrementScore("search", keyword, 1);
			}
		}
	}
}
