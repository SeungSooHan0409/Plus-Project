package com.example.plusproject.domain.reservation.repository;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByOrderByCreatedAt(Pageable pageable);

    boolean existsByAccommodationAndCheckInDate(Accommodation accommodation, LocalDate chekInDate);

}
