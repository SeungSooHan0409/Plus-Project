package com.example.plusproject.domain.accommodation.service;

import com.example.plusproject.domain.accommodation.dto.AccommodationCreateRequestDto;
import com.example.plusproject.domain.accommodation.dto.AccommodationCreateResponseDto;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.repository.AccommodationRepository;
import com.example.plusproject.domain.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
//    private final UserRepository userRepository;

    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Transactional
    public AccommodationCreateResponseDto createAccommodationService(AccommodationCreateRequestDto requestDto) {
        // 1. 데이터 준비
        Long userId = 1L;
//        Long userId = requestDto.getUserId();
        String accommodationName = requestDto.getAccommodationName();
        String address = requestDto.getAddress();
        String city = requestDto.getCity();
        String description = requestDto.getDescription();
        String roomType = requestDto.getRoomType();
        String image = requestDto.getImage();
        String services = requestDto.getServices();
        Double price = requestDto.getPrice();


        // 2. 예외 처리 (호스트 ID가 아니거나 존재하지 않는 사용자 ID일 경우)
        User user = new User();
//        User foundUser = userRepository.findById(userId).
//                orElseThrow(() -> new AccommodationCreateException(404, "사용자ID가 존재하지 않습니다."));
//        if(!foundUser.getRole().equals("Host")) {
//            throw new AccommodationCreateException(400, "호스트가 아닙니다.");
//        }

        // 3. Accommodation 엔티티 생성
        Accommodation newAccommodation = new Accommodation(accommodationName, address, city, description, roomType, image, services, price, user);

        // 4. Accommodation 저장
        Accommodation savedAccommodation = accommodationRepository.save(newAccommodation);

        // 5. 응답 DTO 반환
        return new AccommodationCreateResponseDto(
                savedAccommodation.getId(),
                savedAccommodation.getAccommodationName(),
                savedAccommodation.getAddress(),
                savedAccommodation.getCity(),
                savedAccommodation.getDescription(),
                savedAccommodation.getRoomType(),
                savedAccommodation.getImage(),
                savedAccommodation.getServices(),
                savedAccommodation.getPrice(),
                1L
        );

    }
}
