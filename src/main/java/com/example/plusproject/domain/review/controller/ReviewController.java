package com.example.plusproject.domain.review.controller;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.domain.review.dto.ReviewCreateRequestDto;
import com.example.plusproject.domain.review.dto.ReviewCreateResponseDto;
import com.example.plusproject.domain.review.dto.ReviewDetailResponseDto;
import com.example.plusproject.domain.review.dto.ReviewUpdateRequestDto;
import com.example.plusproject.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")

public class ReviewController {

    private final ReviewService reviewService;

    // 후기 작성 API
    @PostMapping
    public ResponseEntity<ApiResponseDto> createReviewAPI(@RequestBody @Valid ReviewCreateRequestDto reviewCreateRequestDto) {
        ReviewCreateResponseDto reviewCreateResponseDto = reviewService.createReviewService(reviewCreateRequestDto);
        ApiResponseDto responseDto = ApiResponseDto.success("후기 작성이 완료되었습니다.", reviewCreateResponseDto);
        ResponseEntity<ApiResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        return response;
    }

    // 후기 단건 조회 API
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto> getReviewDetailAPI(@PathVariable Long reviewId) {
        ReviewDetailResponseDto reviewDetailResponseDto = reviewService.getReviewDetailService(reviewId);
        ApiResponseDto responseDto = ApiResponseDto.success("후기 조회가 완료되었습니다.", reviewDetailResponseDto);
        ResponseEntity<ApiResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
        return response;
    }

    // 후기 전체 조회 API
    @GetMapping
    public ResponseEntity<String> getReviewListAPI(
            @RequestParam Long accommodationId,
            @RequestParam Long userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ResponseEntity<String> response = new ResponseEntity<>("success", HttpStatus.OK);
        return response;
    }

    // 후기 수정 API
    @PatchMapping("/{reviewId}")
    public ResponseEntity<String> updateReviewAPI(
            @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto,
            @PathVariable Long reviewId
    ) {
        ResponseEntity<String> response = new ResponseEntity<>("success", HttpStatus.OK);
        return response;
    }

    // 후기 삭제 API
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviewAPI(@PathVariable Long reviewsId) {
        ResponseEntity<String> response = new ResponseEntity<>("success", HttpStatus.OK);
        return response;
    }

}
