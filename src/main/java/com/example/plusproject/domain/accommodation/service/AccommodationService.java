package com.example.plusproject.domain.accommodation.service;

import com.example.plusproject.domain.accommodation.dto.AccommodationCreateRequestDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateResponseDto;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.repository.AccommodationRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.enums.UserRole;
import com.example.plusproject.domain.user.service.UserService;
import com.example.plusproject.exception.CustomException;
import com.example.plusproject.exception.ErrorCode;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final UserService userService;
//    private final UserRepository userRepository;

    public AccommodationService(AccommodationRepository accommodationRepository, UserService userService) {
        this.accommodationRepository = accommodationRepository;
        this.userService = userService;
    }

//    @Transactional
//    public AccommodationCreateResponseDto createAccommodationService(AccommodationCreateRequestDto requestDto) {
//        // 1. 데이터 준비
//        Long userId = 1L;
////        Long userId = requestDto.getUserId();
//        String accommodationName = requestDto.getAccommodationName();
//        String address = requestDto.getAddress();
//        String city = requestDto.getCity();
//        String description = requestDto.getDescription();
//        String roomType = requestDto.getRoomType();
//        String image = requestDto.getImage();
//        String services = requestDto.getServices();
//        Double price = requestDto.getPrice();
//
//
//        // 2. 예외 처리 (호스트 ID가 아니거나 존재하지 않는 사용자 ID일 경우)
//        User user = new User();
//        User foundUser = userRepository.findById(userId).
//                orElseThrow(() -> new AccommodationCreateException(404, "사용자ID가 존재하지 않습니다."));
//        if(!foundUser.getRole().equals("Host")) {
//            throw new AccommodationCreateException(400, "호스트가 아닙니다.");
//        }
//
//        // 3. Accommodation 엔티티 생성
//        Accommodation newAccommodation = new Accommodation(accommodationName, address, city, description, roomType, image, services, price, user);
//
//        // 4. Accommodation 저장
//        Accommodation savedAccommodation = accommodationRepository.save(newAccommodation);
//
//        // 5. 응답 DTO 반환
//        return new AccommodationCreateResponseDto(
//                savedAccommodation.getId(),
//                savedAccommodation.getAccommodationName(),
//                savedAccommodation.getAddress(),
//                savedAccommodation.getCity(),
//                savedAccommodation.getDescription(),
//                savedAccommodation.getRoomType(),
//                savedAccommodation.getImage(),
//                savedAccommodation.getServices(),
//                savedAccommodation.getPrice(),
//                1L
//        );
//
//    }

    @Transactional
    public Accommodation createAccommodation(@Valid AccommodationCreateRequestDto dto, Long userId) {

    User user = userService.findUserById(userId);

    if(!user.getRole().equals(UserRole.HOST)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "호스트가 아닙니다.");
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
        return accommodationRepository.save(accommodation);
    }

    // id로 엔티티 반환
    public Accommodation findAccommodationById(Long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(()->new CustomException(ErrorCode.NONEXISTENT_USER));
    }

    public Page<Accommodation> searchAccommodationsByNameOrAddress(String keyword, Pageable pageable) {
        return accommodationRepository.searchAccommodationsByNameOrAddress(keyword, pageable);
    }
}
