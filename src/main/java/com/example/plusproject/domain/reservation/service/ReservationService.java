package com.example.plusproject.domain.reservation.service;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.reservation.dto.ReservationData;
import com.example.plusproject.domain.reservation.dto.ResponseDto;
import com.example.plusproject.domain.reservation.entity.Reservation;
import com.example.plusproject.domain.reservation.repository.ReservationRepository;
import com.example.plusproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    // Accommodation 과 merge 하면 주석 해제
//    private final AccommodationServcie accommodationServcie;

    // 숙소예약 메서드
    public ResponseDto reserveAccommodation(Long guestCount, LocalDate checkInDate, String accommodationAddress) {

        // 임의 유저 생성 - User 와 merge 하면 토큰에서 유저정보 추출하기
        User user = new User(
                "name",
                "nickname",
                "email",
                "password",
                "phoneNumber",
                "residence",
                "role"
        );

        // 임의 숙소 생성 - Accommodation 과 merge 하면 필드에서 숙소주소로 숙소 불러오기
        Accommodation accommodation = new Accommodation(
                "address",
                "city",
                "description",
                "roomType",
                "services",
                10000,
                LocalDateTime.now(),
                user
        );

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

}
