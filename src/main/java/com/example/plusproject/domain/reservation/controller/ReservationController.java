package com.example.plusproject.domain.reservation.controller;

import com.example.plusproject.domain.reservation.dto.PageResponseDto;
import com.example.plusproject.domain.reservation.dto.RequestDto;
import com.example.plusproject.domain.reservation.dto.ResponseDto;
import com.example.plusproject.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    // 숙소 예약 API - 추후 토큰에서 정보추출 필요
    @PostMapping
    public ResponseEntity<ResponseDto> createReservation(
            @RequestBody RequestDto requestDto
            ) {

        return ResponseEntity.ok(
                reservationService.reserveAccommodation(requestDto.getGuestCount(), requestDto.getCheckInDate(), requestDto.getAccommodationAddress())
        );

    }


    // 숙소 목록 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<PageResponseDto> getReservations(
            @RequestParam int page,
            @RequestParam int size
    ) {

        return ResponseEntity.ok(reservationService.getReservationPage(page, size));

    }

}
