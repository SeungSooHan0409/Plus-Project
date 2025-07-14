package com.example.plusproject.domain.reservation.controller;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.config.CustomUserPrincipal;
import com.example.plusproject.domain.reservation.dto.GuestChangeRequestDto;
import com.example.plusproject.domain.reservation.dto.RequestDto;
import com.example.plusproject.domain.reservation.service.ReservationService;
import com.example.plusproject.infra.redis.LettuceLockReservation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final LettuceLockReservation lettuceLockReservation;

    // 숙소 예약 API
    @PostMapping
    public ResponseEntity<ApiResponseDto> createReservation(
            @RequestBody @Valid RequestDto requestDto,
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal
            ) {

        // 토큰에서 userId 추출
        Long userId = userPrincipal.getId();

        // lock 사용
        return ResponseEntity.ok(
                lettuceLockReservation.makingReservation(requestDto.getGuestCount(), requestDto.getCheckInDate(),requestDto.getCheckOutDate() ,requestDto.getAccommodationAddress(), userId)
        );

//        return ResponseEntity.ok(
//                reservationService.reserveAccommodation(requestDto.getGuestCount(), requestDto.getCheckInDate(),requestDto.getCheckOutDate() ,requestDto.getAccommodationAddress(), userId)
//        );

    }


    // 예약 목록 조회 API
    @GetMapping
    public ResponseEntity<ApiResponseDto> getReservations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(reservationService.getReservationPage(page, size));

    }


    // 예약 인원 변경 API
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDto> changeReservation(
            @RequestBody @Valid GuestChangeRequestDto requestDto,
            @PathVariable Long id
            ) {

        return ResponseEntity.ok(reservationService.changeGuests(id, requestDto.getGuestCount()));

    }


    // 예약 취소 API
    // User 병합후 로그인 여부확인 해야함.
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> cancelReservation(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(reservationService.deleteReservation(id));

    }
}
