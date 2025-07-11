package com.example.plusproject.domain.accommodation.repository;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccommodationRepositoryCustom {
    Page<Accommodation> searchAccommodationsByNameOrAddress(String keyword, Pageable pageable);

    long countAccommodationsByNameOrAddress(String keyword);
}
