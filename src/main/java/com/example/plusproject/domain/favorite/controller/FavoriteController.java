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

}
