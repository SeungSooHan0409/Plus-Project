package com.example.plusproject.domain.favorite.controller;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.config.CustomUserPrincipal;
import com.example.plusproject.domain.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;


    // 찜하기
    @PostMapping("/{accommodationId}")
    public ResponseEntity<ApiResponseDto> createFavorite(
            @PathVariable Long accommodationId,
            @AuthenticationPrincipal CustomUserPrincipal principal
            ) {

        // userId 조회
        Long userId = principal.getId();

        return ResponseEntity.ok(favoriteService.createFavorite(accommodationId, userId));

    }


    // 찜목록 조회
    @GetMapping
    public ResponseEntity<ApiResponseDto> getFavorites(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(favoriteService.getFavorites(page, size));

    }


    // 찜 취소하기
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteFavorite(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(favoriteService.deleteFavorite(id));

    }

}
