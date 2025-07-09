package com.example.plusproject.domain.accommodation.controller;

import com.example.plusproject.config.CustomUserPrincipal;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateRequestDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateResponseDto;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.service.AccommodationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {
    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

//    @PostMapping // 컨트롤러 : jwt -> userId 추출 // 서비스: role이 host인지 아닌지 검증
//    public ResponseEntity<AccommodationCreateResponseDto> createAccommodationAPI(
//            @Valid @RequestBody AccommodationCreateRequestDto requestDto) {
//        AccommodationCreateResponseDto responseDto = accommodationService.createAccommodationService(requestDto);
//        return ResponseEntity.ok(responseDto);
//    }

    @PostMapping
    public ResponseEntity<Accommodation> createAccommodation(
            @RequestBody @Valid AccommodationCreateRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        Accommodation created = accommodationService.createAccommodation(dto, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


}
