package com.example.plusproject.domain.trending.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.domain.trending.dto.TrendingResponseDto;
import com.example.plusproject.domain.trending.service.TrendingService;
import com.example.plusproject.domain.user.dto.AuthResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TrendingController {

	private final TrendingService trendingService;

	@PostMapping("/api/test")
	public ResponseEntity<Void> test(@RequestParam(required = false) String search) {

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/api/trending")
	public ResponseEntity<ApiResponseDto> getTrending() {

		List<TrendingResponseDto> trending = trendingService.getTrending();

		ApiResponseDto success = ApiResponseDto.success("인기 검색어 순위 조회 결과입니다.", trending);

		return ResponseEntity.status(HttpStatus.OK).body(success);
	}

}
