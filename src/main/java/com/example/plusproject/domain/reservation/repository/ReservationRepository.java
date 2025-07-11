package com.example.plusproject.domain.reservation.repository;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.reservation.entity.Reservation;
import com.example.plusproject.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByOrderByCreatedAt(Pageable pageable);

    Optional<Reservation> findByUserAndId(User user, Long reservationId);

    boolean existsByAccommodationAndCheckInDate(Accommodation accommodation, LocalDate chekInDate);

}
