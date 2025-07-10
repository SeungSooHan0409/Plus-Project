package com.example.plusproject.domain.favorite.service;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.service.AccommodationService;
import com.example.plusproject.domain.favorite.dto.FavoriteData;
import com.example.plusproject.domain.favorite.entity.Favorite;
import com.example.plusproject.domain.favorite.repository.FavoriteRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final AccommodationService accommodationService;

    // 찜하기 메서드
    public ApiResponseDto createFavorite(Long accommodationId, Long userId) {

        // 찜하려는 유저 조회
        User user = userService.findUserById(userId);

        // 찜하려는 숙소 조회
        Accommodation accommodation = accommodationService.findAccommodationById(accommodationId);

        // 찜 생성 및 저장
        Favorite favorite = new Favorite(user, accommodation);
        favoriteRepository.save(favorite);

        // data 생성
        FavoriteData data = new FavoriteData(favorite.getAccommodation().getAccommodationName(), favorite.getUser().getNickname());

        // 응답 반환
        return ApiResponseDto.success("찜하기 성공!", data);

    }

}
