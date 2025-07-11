package com.example.plusproject.domain.reservation.repository;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.reservation.entity.Reservation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByOrderByCreatedAt(Pageable pageable);

    boolean existsByAccommodationAndCheckInDate(Accommodation accommodation, LocalDate chekInDate);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.accommodation.address = :accommodationAddress " +
            "AND NOT (:checkOutDate <= r.checkInDate OR :checkInDate >= r.checkOutDate)")
    List<Reservation> findOverlapped(
            @Param("accommodationAddress") String accommodationAddress,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );

}
