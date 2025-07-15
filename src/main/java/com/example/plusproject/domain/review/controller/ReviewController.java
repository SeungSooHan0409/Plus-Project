package com.example.plusproject.domain.review.controller;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.config.CustomUserPrincipal;
import com.example.plusproject.domain.review.dto.*;
import com.example.plusproject.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")

public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 후기 작성 API
     *
     * @param reviewCreateRequestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto> createReviewAPI(
            @RequestBody @Valid ReviewCreateRequestDto reviewCreateRequestDto,
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        Long userPrincipalId = userPrincipal.getId();
        ReviewCreateResponseDto reviewCreateResponseDto = reviewService.createReviewService(reviewCreateRequestDto, userPrincipalId);
        ApiResponseDto responseDto = ApiResponseDto.success("후기 작성이 완료되었습니다.", reviewCreateResponseDto);
        ResponseEntity<ApiResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        return response;
    }

    /**
     * 후기 단건 조회 API
     *
     * @param reviewId
     * @return
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto> getReviewDetailAPI(@PathVariable Long reviewId) {
        ReviewDetailResponseDto reviewDetailResponseDto = reviewService.getReviewDetailService(reviewId);
        ApiResponseDto responseDto = ApiResponseDto.success("후기 조회가 완료되었습니다.", reviewDetailResponseDto);
        ResponseEntity<ApiResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
        return response;
    }

    /**
     * 후기 전체 조회 API
     *
     * @param accommodationId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto> getReviewListAPI(
            @RequestParam(value = "accommodationId", required = false) Long accommodationId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "page", defaultValue = "1") int page,  // 클라이언트는 1부터 시작
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        // 검증 로직
        int adjustedPage = Math.max(page - 1, 0);

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());

        ReviewPageResponseDto reviewPageResponseDto = reviewService.getReviewPageService(accommodationId, userId, pageable);

        ApiResponseDto responseDto = ApiResponseDto.success("후기 목록 조회가 완료되었습니다.", reviewPageResponseDto);

        ResponseEntity<ApiResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
        return response;
    }

    /**
     * 후기 수정 API
     *
     * @param reviewUpdateRequestDto
     * @param reviewId
     * @return
     */
    @PatchMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto> updateReviewAPI(
            @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        Long userPrincipalId = userPrincipal.getId();
        ReviewUpdateResponseDto reviewUpdateResponseDto = reviewService.updateReviewService(reviewUpdateRequestDto, reviewId, userPrincipalId);
        ApiResponseDto responseDto = ApiResponseDto.success("후기 수정이 완료되었습니다.", reviewUpdateResponseDto);
        ResponseEntity<ApiResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
        return response;
    }

    /**
     * 후기 삭제 API
     *
     * @param reviewId
     * @return
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto> deleteReviewAPI(@PathVariable Long reviewId, @AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        Long userPrincipalId = userPrincipal.getId();
        reviewService.deleteReviewService(reviewId, userPrincipalId);
        ApiResponseDto responseDto = ApiResponseDto.success("후기 삭제가 완료되었습니다.", Collections.EMPTY_MAP);
        // todo: null 안 보이게 코드 수정
        ResponseEntity<ApiResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
        return response;
    }

}
