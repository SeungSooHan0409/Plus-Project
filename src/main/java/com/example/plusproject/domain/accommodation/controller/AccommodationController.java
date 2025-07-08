package com.example.plusproject.domain.accommodation.controller;

import com.example.plusproject.domain.accommodation.dto.AccommodationCreateRequestDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateResponseDto;
import com.example.plusproject.domain.accommodation.service.AccommodationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accomodations")
public class AccommodationController {
    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @PostMapping // 컨트롤러 : jwt -> userId 추출 // 서비스: role이 host인지 아닌지 검증
    public ResponseEntity<AccommodationCreateResponseDto> createAccommodationAPI(@Valid @RequestBody AccommodationCreateRequestDto requestDto) {
        AccommodationCreateResponseDto responseDto = accommodationService.createAccommodationService(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
