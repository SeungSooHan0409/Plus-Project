package com.example.plusproject.domain.accommodation.controller;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.config.CustomUserPrincipal;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateRequestDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateResponseDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationUpdateRequestDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationUpdateResponseDto;
import com.example.plusproject.domain.accommodation.service.AccommodationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {
    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createAccommodation(
            @RequestBody @Valid AccommodationCreateRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        AccommodationCreateResponseDto responseDto = accommodationService.createAccommodation(dto, userPrincipal.getId());
        ApiResponseDto response = ApiResponseDto.success("숙소 등록이 완료되었습니다.", responseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/v1/search")
    public ResponseEntity<Page<AccommodationCreateResponseDto>> searchAccommodationsV1(
            @RequestParam(required = false) String keyword,
            @PageableDefault Pageable pageable
    ) {
        Page<AccommodationCreateResponseDto> accommodationsPage =
                accommodationService.searchAccommodationsByNameOrAddressV1(keyword, pageable);
        return ResponseEntity.ok(accommodationsPage);
    }

//    @GetMapping("/v2/search")
//    public ResponseEntity<Page<Accommodation>> searchAccommodationsV2(
//            @RequestParam(required = false) String keyword,
//            @PageableDefault Pageable pageable
//    ) {
//        Page<Accommodation> accommodationsPage = accommodationService.searchAccommodationsByNameOrAddressV2(keyword, pageable);
//        return ResponseEntity.ok(accommodationsPage);
//    }

    @GetMapping("/v3/search")
    public ResponseEntity<Page<AccommodationCreateResponseDto>> searchAccommodationsV3(
            @RequestParam(required = false) String keyword,
            @PageableDefault Pageable pageable
    ) {
        List<AccommodationCreateResponseDto> list = accommodationService.searchAccommodationsByNameOrAddressV3(keyword, pageable);
        long total = accommodationService.countAccommodations(keyword);
        return ResponseEntity.ok(new PageImpl<>(list, pageable, total));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateAccommodation(
            @PathVariable Long id,
            @RequestBody AccommodationUpdateRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        AccommodationUpdateResponseDto responseDto = accommodationService.updateAccommodation(id, dto, userPrincipal.getId());
        ApiResponseDto response = ApiResponseDto.success("숙소 상세 정보 수정이 완료되었습니다.", responseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteAccommodation(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        accommodationService.deleteAccommodation(id, userPrincipal.getId());
        ApiResponseDto response = ApiResponseDto.success("숙소 정보 삭제가 완료되었습니다.", null);
        return ResponseEntity.ok(response);
    }
}
