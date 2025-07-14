package com.example.plusproject.domain.accommodation.service;

import com.example.plusproject.common.exception.CustomException;
import com.example.plusproject.domain.accommodation.dto.*;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.repository.AccommodationRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.enums.UserRole;
import com.example.plusproject.domain.user.service.UserService;
import com.example.plusproject.common.exception.ErrorType;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @CacheEvict(value = {"accommodationSearchCacheV3", "keywordCountCache", "citySearchCache", "cityCountCache"}, allEntries = true)
    public AccommodationUpdateResponseDto updateAccommodation(Long id, AccommodationUpdateRequestDto dto, Long userId) {

        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorType.NONEXISTENT_ACCOMMODATION));

        if(!userId.equals(accommodation.getUser().getId())) {
            throw new CustomException(ErrorType.NO_AUTHORITY_FOR_REVISION);
        }

        String newAccommodationName = dto.getAccommodationName();
        String newAddress = dto.getAddress();
        String newCity = dto.getCity();
        String newDescription = dto.getDescription();
        String newRoomType = dto.getRoomType();
        String newImage = dto.getImage();
        String newServices = dto.getServices();
        Double newPrice = dto.getPrice();

        if(dto.hasValidAccommodationName()) { accommodation.changeAccommodationName(newAccommodationName); }
        if(dto.hasValidAddress()) { accommodation.changeAddress(newAddress); }
        if(dto.hasValidCity()) { accommodation.changeCity(newCity); }
        if(dto.hasValidDescription()) { accommodation.changeDescription(newDescription); }
        if(dto.hasValidRoomType()) { accommodation.changeRoomType(newRoomType); }
        if(dto.hasValidImage()) { accommodation.changeImage(newImage); }
        if(dto.hasValidServices()) { accommodation.changeServices(newServices); }
        if(dto.hasValidPrice()) { accommodation.changePrice(newPrice); }

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        return AccommodationUpdateResponseDto.from(savedAccommodation);
    }

    public void deleteAccommodation(Long id, Long userId) {

        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorType.NONEXISTENT_ACCOMMODATION));

        if(!userId.equals(accommodation.getUser().getId())) {
            throw new CustomException(ErrorType.NO_AUTHORITY_FOR_DELETION);
        }

        accommodationRepository.delete(accommodation);
    }

    // id로 엔티티 반환
    public Accommodation findAccommodationById(Long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(()->new CustomException(ErrorType.NONEXISTENT_ACCOMMODATION));
    }

    public Page<AccommodationKeywordSearchResponseDto> searchAccommodationsByNameOrAddressV1(String keyword, Pageable pageable) {
        Page<Accommodation> accommodations = accommodationRepository.searchAccommodationsByNameOrAddress(keyword, pageable);
        return accommodations.map(AccommodationKeywordSearchResponseDto::from);
    }

//    @Cacheable(value = "accommodationSearchCache", key = "#keyword")
//    public Page<Accommodation> searchAccommodationsByNameOrAddressV2(String keyword, Pageable pageable) {
//        return accommodationRepository.searchAccommodationsByNameOrAddress(keyword, pageable);
//    }

    @Cacheable(value = "accommodationSearchCacheV3", key = "#keyword + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public List<AccommodationKeywordSearchResponseDto> searchAccommodationsByNameOrAddressV3(String keyword, Pageable pageable) {
        Page<Accommodation> result = accommodationRepository.searchAccommodationsByNameOrAddress(keyword, pageable);
        return result.stream()
                .map(AccommodationKeywordSearchResponseDto::from)
//                .toList();
                .collect(Collectors.toList());
    }

    // address 로 엔티티 반환
    public Accommodation findAccommodationByAddress(String address) {
        return accommodationRepository.findByAddress(address);
    }

    @Cacheable(value = "keywordCountCache", key = "#keyword")
    public long countAccommodations(String keyword) {
        return accommodationRepository.countAccommodationsByNameOrAddress(keyword);
    }

    public Page<AccommodationSearchResponseDto> searchAccommodationsByCityV1(String city, Pageable pageable) {
        return accommodationRepository.searchByCity(city, pageable);
    }

    @Cacheable(value = "citySearchCache", key = "#city + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public List<AccommodationSearchResponseDto> searchAccommodationsByCityV3(String city, Pageable pageable) {
        return accommodationRepository.searchByCity(city, pageable).getContent();
    }

    @Cacheable(value = "cityCountCache", key = "#city")
    public long countAccommodationsByCity(String city) {
        return accommodationRepository.countAccommodationsByCity(city);
    }
}
