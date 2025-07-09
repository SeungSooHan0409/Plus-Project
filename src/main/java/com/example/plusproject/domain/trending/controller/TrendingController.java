package com.example.plusproject.domain.trending.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TrendingController {

	@PostMapping("/api/test")
	public ResponseEntity<Void> test(@RequestParam(required = false) String search) {

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
