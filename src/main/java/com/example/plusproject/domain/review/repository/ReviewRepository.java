package com.example.plusproject.domain.review.repository;

import com.example.plusproject.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByReservationId(Long reservationId);

    Optional<Review> findByUserIdAndId(Long userId, Long reviewId);

    Page<Review> findAllByAccommodationId(Long accommodationId, Pageable pageable);

    Page<Review> findAllByUserId(Long userId, Pageable pageable);

    Page<Review> findAllByAccommodationIdAndUserId(Long accommodationId, Long userId, Pageable pageable);

}
