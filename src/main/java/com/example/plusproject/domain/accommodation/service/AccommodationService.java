package com.example.plusproject.domain.accommodation.service;

import com.example.plusproject.common.exception.CustomException;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateRequestDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateResponseDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationUpdateRequestDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationUpdateResponseDto;
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

    public AccommodationUpdateResponseDto updateAccommodation(Long id, AccommodationUpdateRequestDto dto, Long userId) {
        User user = userService.findUserById(userId);

        if(!user.getRole().equals(UserRole.HOST)) {
            throw new CustomException(ErrorType.INVALID_USER);
        }

        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(()->new CustomException(ErrorType.NONEXISTENT_ACCOMMODATION));

        String newAccommodationName = dto.getAccommodationName();
        String newAddress = dto.getAddress();
        String newCity = dto.getCity();
        String newDescription = dto.getDescription();
        String newRoomType = dto.getRoomType();
        String newImage = dto.getImage();
        String newServices = dto.getServices();
        Double newPrice = dto.getPrice();

        if(newAccommodationName != null && !newAccommodationName.isBlank()) {
            accommodation.changeAccommodationName(newAccommodationName);
        }

        if(newAddress != null && !newAddress.isBlank()) {
            accommodation.changeAddress(newAddress);
        }

        if(newCity != null && !newCity.isBlank()) {
            accommodation.changeCity(newCity);
        }

        if(newDescription != null && !newDescription.isBlank()) {
            accommodation.changeDescription(newDescription);
        }

        if(newRoomType != null && !newRoomType.isBlank()) {
            accommodation.changeRoomType(newRoomType);
        }

        if(newImage != null && !newImage.isBlank()) {
            accommodation.changeImage(newImage);
        }

        if(newServices != null && !newServices.isBlank()) {
            accommodation.changeServices(newServices);
        }

        if(newPrice != null) {
            accommodation.changePrice(newPrice);
        }

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        return AccommodationUpdateResponseDto.from(savedAccommodation);
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
