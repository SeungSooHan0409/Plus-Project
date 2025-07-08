package com.example.plusproject.domain.reservation.controller;

import com.example.plusproject.domain.reservation.dto.GuestChangeRequestDto;
import com.example.plusproject.domain.reservation.dto.PageResponseDto;
import com.example.plusproject.domain.reservation.dto.RequestDto;
import com.example.plusproject.domain.reservation.dto.ResponseDto;
import com.example.plusproject.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    // 숙소 예약 API
    // 토큰에서 정보추출 필요
    // User 병합후 로그인 여부확인 해야함.
    @PostMapping
    public ResponseEntity<ResponseDto> createReservation(
            @RequestBody RequestDto requestDto
            ) {

        return ResponseEntity.ok(
                reservationService.reserveAccommodation(requestDto.getGuestCount(), requestDto.getCheckInDate(), requestDto.getAccommodationAddress())
        );

    }


    // 숙소 목록 조회 API
    // User 병합후 로그인 여부확인 해야함.
    @GetMapping("/{id}")
    public ResponseEntity<PageResponseDto> getReservations(
            @RequestParam int page,
            @RequestParam int size
    ) {

        return ResponseEntity.ok(reservationService.getReservationPage(page, size));

    }


    // 예약 인원 변경 API
    // User 병합후 로그인 여부확인 해야함.
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> changeReservation(
            @RequestBody GuestChangeRequestDto requestDto,
            @PathVariable Long id
            ) {

        return ResponseEntity.ok(reservationService.changeGuests(id, requestDto.getGuestCount()));

    }


    // 예약 취소 API
    // User 병합후 로그인 여부확인 해야함.
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> cancelReservation(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(reservationService.deleteReservation(id));

    }
}
