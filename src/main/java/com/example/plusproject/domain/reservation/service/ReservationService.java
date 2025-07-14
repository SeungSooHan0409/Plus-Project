package com.example.plusproject.domain.reservation.service;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.common.exception.CustomException;
import com.example.plusproject.common.exception.ErrorType;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.service.AccommodationService;
import com.example.plusproject.domain.reservation.dto.ReservationData;
import com.example.plusproject.domain.reservation.entity.Reservation;
import com.example.plusproject.domain.reservation.repository.ReservationRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final AccommodationService accommodationService;


    // 숙소예약 메서드
    @Transactional
    public ApiResponseDto reserveAccommodation(Long guestCount, LocalDate checkInDate, LocalDate checkOutDate, String accommodationAddress, Long userId) {

        // 유저 조회
        User user = userService.findUserById(userId);

        // 숙소 조회
        Accommodation accommodation = accommodationService.findAccommodationByAddress(accommodationAddress);

        // 체크인 날짜 < 체크아웃 날짜
        if(!(checkInDate.isBefore(checkOutDate))) {
            throw new CustomException(ErrorType.INVALID_DATE);
        }

        // 겹치는 기간에 예약 불가능
        if(!(reservationRepository.findOverlapped(accommodationAddress, checkInDate, checkOutDate).isEmpty())) {
            throw new CustomException(ErrorType.ASSIGNED_DATE);
        }

        // 수용인원 초과시 예외발생
        if(guestCount > accommodation.getCapacity()) {
            throw new CustomException(ErrorType.UNACCEPTABLE_CAPACITY);
        }

        // 예약 생성
        Reservation reservation = new Reservation(accommodation, user, checkInDate, checkOutDate,guestCount);

        // 예약 저장
        reservationRepository.save(reservation);

        // data 생성
        ReservationData data = new ReservationData(
                reservation.getId(),
                reservation.getAccommodation().getAddress(),
                reservation.getGuestCount(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate());

        // Response 반환
        return ApiResponseDto.success("예약 성공!", data);

    }


    // 예약 목록 조회 메서드
    public ApiResponseDto getReservationPage(int page, int size) {

        // Pageable 생성
        Pageable pageable = PageRequest.of(page - 1, size);

        // 예약목록을 Page 로 조회
        Page<Reservation> reservations = reservationRepository.findAllByOrderByCreatedAt(pageable);

        // data 생성
        Page<ReservationData> data = reservations.map(Reservation::toData);

        return ApiResponseDto.success("조회 성공!", data);



    }


    // 예약 인원 변경 메서드
    @Transactional
    public ApiResponseDto changeGuests(Long id, Long guestCount) {

        // 변경하려는 인원이 0 이하일 경우 예외처리
        if(guestCount <= 0) {
            throw new CustomException(ErrorType.INVALID_GUESTCOUNT);
        }

        // 예약 조회하기
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.RESERVATION_NOT_FOUND));

        // 수용인원 초과시 예외발생
        if(guestCount > reservation.getAccommodation().getCapacity()) {
            throw new CustomException(ErrorType.UNACCEPTABLE_CAPACITY);
        }

        // 인원 변경하기
        reservation.setGuestCount(guestCount);
        reservationRepository.save(reservation);

        // data 생성
        ReservationData data = new ReservationData(reservation.getId(),
                reservation.getAccommodation().getAddress(),
                reservation.getGuestCount(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate());

        return ApiResponseDto.success("변경 성공!", data);

    }


    // 예약 삭제 메서드
    @Transactional
    public ApiResponseDto deleteReservation(Long id) {

        // 삭제할 예약 조회 (예외처리용)
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.RESERVATION_NOT_FOUND));

        // 예약 삭제 수행
        reservationRepository.delete(reservation);

        // 응답 반환
        return ApiResponseDto.success("삭제 성공!", null);

    }

    // id 로 Entity 반환
    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.RESERVATION_NOT_FOUND));
    }

    // User, reservationId로 Entity 반환
    public Reservation findReservationByUserAndId(User user, Long reservationId) {
        return reservationRepository.findByUserAndId(user, reservationId)
                .orElseThrow(() -> new CustomException(ErrorType.RESERVATION_NOT_FOUND_OR_FORBIDDEN));
    }

}
