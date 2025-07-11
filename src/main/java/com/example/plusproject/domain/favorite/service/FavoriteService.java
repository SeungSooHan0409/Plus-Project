package com.example.plusproject.domain.favorite.service;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.common.exception.CustomException;
import com.example.plusproject.common.exception.ErrorType;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.accommodation.service.AccommodationService;
import com.example.plusproject.domain.favorite.dto.FavoriteData;
import com.example.plusproject.domain.favorite.entity.Favorite;
import com.example.plusproject.domain.favorite.repository.FavoriteRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    // 찜목록 조회 메서드
    public ApiResponseDto getFavorites(int page, int size) {

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page - 1, size);

        // 찜목록 가져오기
        Page<Favorite> favorites = favoriteRepository.findAllByOrderByModifiedAt(pageable);

        // data 생성
        Page<FavoriteData> data = favorites.map(Favorite::toData);

        // 응답 반환
        return ApiResponseDto.success("조회 성공!", data);

    }


    // 찜 취소 메서드
    public ApiResponseDto deleteFavorite(Long id) {

        // 삭제할 찜 조회
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(() -> new CustomException(ErrorType.FAVORITE_NOT_FOUND));

        // 찜 삭제 수행
        favoriteRepository.delete(favorite);

        // 응답 반환
        return ApiResponseDto.success("삭제 성공!", null);

    }

}
