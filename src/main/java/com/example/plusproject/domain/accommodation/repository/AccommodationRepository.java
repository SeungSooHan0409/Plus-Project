package com.example.plusproject.domain.accommodation.repository;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Accommodation findByAddress(String address);
}
