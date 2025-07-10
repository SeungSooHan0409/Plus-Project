package com.example.plusproject.domain.accommodation.service;

import com.example.plusproject.common.exception.CustomException;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateRequestDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateResponseDto;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.repository.AccommodationRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.enums.UserRole;
import com.example.plusproject.domain.user.service.UserService;
import com.example.plusproject.common.exception.ErrorType;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final UserService userService;

    public AccommodationService(AccommodationRepository accommodationRepository, UserService userService) {
        this.accommodationRepository = accommodationRepository;
        this.userService = userService;
    }

    public AccommodationCreateResponseDto createAccommodation(@Valid AccommodationCreateRequestDto dto, Long userId) {
        User user = userService.findUserById(userId);

        if(!user.getRole().equals(UserRole.HOST)) {
            throw new CustomException(ErrorType.INVALID_USER);
        }

        Accommodation accommodation = new Accommodation(
                dto.getAccommodationName(),
                dto.getAddress(),
                dto.getCity(),
                dto.getImage(),
                dto.getDescription(),
                dto.getRoomType(),
                dto.getServices(),
                dto.getPrice(),
                user
        );

        Accommodation saved = accommodationRepository.save(accommodation);
        return AccommodationCreateResponseDto.from(saved);
    }


    // id로 엔티티 반환
    public Accommodation findAccommodationById(Long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(()->new CustomException(ErrorType.NONEXISTENT_ACCOMMODATION));
    }

    public Page<Accommodation> searchAccommodationsByNameOrAddress(String keyword, Pageable pageable) {
        return accommodationRepository.searchAccommodationsByNameOrAddress(keyword, pageable);
    }

    // address 로 엔티티 반환
    public Accommodation findAccommodationByAddress(String address) {
        return accommodationRepository.findByAddress(address);
    }
}
