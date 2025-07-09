package com.example.plusproject.domain.reservation.service;

import com.example.plusproject.common.exception.CustomException;
import com.example.plusproject.common.exception.ErrorType;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.repository.AccommodationRepository;
import com.example.plusproject.domain.accommodation.service.AccommodationService;
import com.example.plusproject.domain.reservation.dto.PageResponseDto;
import com.example.plusproject.domain.reservation.dto.ReservationData;
import com.example.plusproject.domain.reservation.dto.ResponseDto;
import com.example.plusproject.domain.reservation.entity.Reservation;
import com.example.plusproject.domain.reservation.exception.ReservationNotFound;
import com.example.plusproject.domain.reservation.repository.ReservationRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final AccommodationService accommodationService;


    // 숙소예약 메서드
    // User 병합후 로그인 여부확인 해야함.
    // User, Accmodation 병합후 더미데이터를 실제데이터로 교체하는 로직 작성해야함.
    public ResponseDto reserveAccommodation(Long guestCount, LocalDate checkInDate, String accommodationAddress, Long userId) {

        // 유저 조회
        User user = userService.findUserById(userId);

        // 숙소 조회
        Accommodation accommodation = accommodationService.findAccommodationByAddress(accommodationAddress);

        // 예약 생성
        Reservation reservaion = new Reservation(accommodation, user, checkInDate, guestCount);

        // 예약 저장
        reservationRepository.save(reservaion);

        // data 생성
        ReservationData data = new ReservationData(
                reservaion.getId(),
                reservaion.getAccommodation().getAddress(),
                reservaion.getGuestCount(),
                reservaion.getCheckInDate());

        // Response 생성
        ResponseDto responseDto = new ResponseDto(true, "예약 성공!", data, LocalDateTime.now());

        return responseDto;

    }


    // 예약 목록 조회 메서드
    // User 병합후 로그인 여부확인 해야함.
    public PageResponseDto getReservationPage(int page, int size) {

        // Pageable 생성
        Pageable pageable = PageRequest.of(page - 1, size);

        // 예약목록을 Page 로 조회
        Page<Reservation> reservations = reservationRepository.findAllByOrderByCreatedAt(pageable);

        // data 생성
        Page<ReservationData> data = reservations.map(Reservation::toData);

        return new PageResponseDto(true, "조회 성공!", data, LocalDateTime.now());



    }


    // 예약 인원 변경 메서드
    // User 병합후 로그인 여부확인 해야함.
    public ResponseDto changeGuests(Long id, Long guestCount) {

        // 예약 조회하기
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.RESVERVATION_NOT_FOUND));

        // 인원 변경하기
        reservation.setGuestCount(guestCount);
        reservationRepository.save(reservation);

        // data 생성
        ReservationData data = new ReservationData(reservation.getId(),
                reservation.getAccommodation().getAddress(),
                reservation.getGuestCount(),
                reservation.getCheckInDate());

        return new ResponseDto(true, "변경 성공!", data, LocalDateTime.now());

    }


    // 예약 삭제 메서드
    // User 병합후 로그인 여부확인 해야함.
    public ResponseDto deleteReservation(Long id) {

        // 삭제할 예약 조회 (예외처리용)
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.RESVERVATION_NOT_FOUND));

        // 예약 삭제 수행
        reservationRepository.delete(reservation);

        // 응답 반환
        return new ResponseDto(true, "삭제 성공!", null, LocalDateTime.now());

    }

}
