package com.example.plusproject.domain.review.service;

import com.example.plusproject.common.exception.CustomException;
import com.example.plusproject.common.exception.ErrorType;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.reservation.entity.Reservation;
import com.example.plusproject.domain.reservation.service.ReservationService;
import com.example.plusproject.domain.review.dto.*;
import com.example.plusproject.domain.review.entity.Review;
import com.example.plusproject.domain.review.repository.ReviewRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationService reservationService;
    private final UserService userService;

    /**
     * 후기 작성 기능
     *
     * @param reviewCreateRequestDto
     * @return
     */
    @Transactional
    public ReviewCreateResponseDto createReviewService(ReviewCreateRequestDto reviewCreateRequestDto, Long userPrincipalId) {

        // 데이터 준비
        double rating = reviewCreateRequestDto.getRating();
        String content = reviewCreateRequestDto.getContent();
        String imageUrl = reviewCreateRequestDto.getImageUrl();
        Long requestDtoReservationId = reviewCreateRequestDto.getReservationId();

        // 유저 ID로 예약 가져오기
        User user = userService.findUserById(userPrincipalId);
        Reservation reservation = reservationService.findReservationByUserAndId(user, requestDtoReservationId);
        Long reservationId = reservation.getId();
        Accommodation reservationAccommodation = reservation.getAccommodation();

        // 검증로직 작성
        boolean exists = reviewRepository.existsByReservationId(reservationId);
        if (exists) {
            throw new CustomException(ErrorType.REVIEW_ALREADY_EXISTS);
        }

        // 엔티티 만들기
        Review review = new Review(rating, content, imageUrl, user, reservation, reservationAccommodation);

        // 저장
        Review savedReview = reviewRepository.save(review); // insert

        // responseDto 만들기
        Long savedReviewId = savedReview.getId();
        User savedReviewUser = savedReview.getUser();
        String savedReviewUserNickname = savedReviewUser.getNickname();
        double savedReviewRating = savedReview.getRating();
        String savedReviewContent = savedReview.getContent();
        LocalDateTime savedReviewCreatedAt = savedReview.getCreatedAt();

        ReviewCreateResponseDto responseDto = new ReviewCreateResponseDto(savedReviewId, savedReviewUserNickname, savedReviewRating, savedReviewContent, savedReviewCreatedAt);

        // responseDto 반환
        return responseDto;
    }

    /**
     * 후기 단건 조회 기능
     *
     * @param reviewId
     * @return
     */
    public ReviewDetailResponseDto getReviewDetailService(Long reviewId) {

        // 데이터 준비
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorType.REVIEW_NOT_FOUND));

        User reviewUser = review.getUser();
        Long reviewUserId = reviewUser.getId();
        String reviewUserNickname = reviewUser.getNickname();

        Reservation reviewReservation = review.getReservation();
        Accommodation reviewReservationAccommodation = reviewReservation.getAccommodation();

        Long reviewAccommodationId = reviewReservationAccommodation.getId();
        String reviewAccommodationAccommodationName = reviewReservationAccommodation.getAccommodationName();
        Double reviewRating = review.getRating();
        String reviewContent = review.getContent();
        String reviewImageUrl = review.getImageUrl();
        LocalDateTime reviewCreatedAt = review.getCreatedAt();

        UserDto userDto = new UserDto(reviewUserId, reviewUserNickname);
        AccommodationDto accommodationDto = new AccommodationDto(reviewAccommodationId, reviewAccommodationAccommodationName);

        ReviewDetailResponseDto responseDto = new ReviewDetailResponseDto(reviewId, userDto, accommodationDto, reviewRating, reviewContent, reviewImageUrl, reviewCreatedAt);
        return responseDto;

    }

    /**
     * 후기 전체 조회 기능
     *
     * @param accommodationId
     * @param userId
     * @param pageable
     * @return
     */
    public ReviewPageResponseDto getReviewPageService(Long accommodationId, Long userId, Pageable pageable) {

        // 데이터 준비
        if (accommodationId != null && userId == null) {
            Page<Review> accommodationReviewPage = reviewRepository.findAllByAccommodationId(accommodationId, pageable);
            List<ReviewDetailResponseDto> reviewList = accommodationReviewPage.getContent().stream()
                    .map(review -> new ReviewDetailResponseDto(
                            review.getId(),
                            null,
                            new AccommodationDto(review.getAccommodation().getId(), review.getAccommodation().getAccommodationName()),
                            review.getRating(),
                            review.getContent(),
                            review.getImageUrl(),
                            review.getCreatedAt()
                    ))
                    .collect(Collectors.toList());

            PageDto pageDto = new PageDto(
                    accommodationReviewPage.getNumber() + 1,
                    accommodationReviewPage.getSize(),
                    accommodationReviewPage.getTotalPages(),
                    accommodationReviewPage.getTotalElements());

            ReviewPageResponseDto responseDto = new ReviewPageResponseDto<>(reviewList, pageDto);

            return responseDto;
        }

        if (accommodationId == null && userId != null) {
            Page<Review> userReviewPage = reviewRepository.findAllByUserId(userId, pageable);
            List<ReviewDetailResponseDto> reviewList = userReviewPage.getContent().stream()
                    .map(review -> new ReviewDetailResponseDto(
                            review.getId(),
                            new UserDto(review.getUser().getId(), review.getUser().getNickname()),
                            null,
                            review.getRating(),
                            review.getContent(),
                            review.getImageUrl(),
                            review.getCreatedAt()
                    ))
                    .collect(Collectors.toList());

            PageDto pageDto = new PageDto(
                    userReviewPage.getNumber() + 1,
                    userReviewPage.getSize(),
                    userReviewPage.getTotalPages(),
                    userReviewPage.getTotalElements());

            ReviewPageResponseDto responseDto = new ReviewPageResponseDto<>(reviewList, pageDto);

            return responseDto;
        }

        if (accommodationId != null && userId != null) {
            Page<Review> accommodationAndUserReviewPage = reviewRepository.findAllByAccommodationIdAndUserId(accommodationId, userId, pageable);
            List<ReviewDetailResponseDto> reviewList = accommodationAndUserReviewPage.getContent().stream()
                    .map(review -> new ReviewDetailResponseDto(
                            review.getId(),
                            new UserDto(review.getUser().getId(), review.getUser().getNickname()),
                            new AccommodationDto(review.getAccommodation().getId(), review.getAccommodation().getAccommodationName()),
                            review.getRating(),
                            review.getContent(),
                            review.getImageUrl(),
                            review.getCreatedAt()
                    ))
                    .collect(Collectors.toList());

            PageDto pageDto = new PageDto(
                    accommodationAndUserReviewPage.getNumber() + 1,
                    accommodationAndUserReviewPage.getSize(),
                    accommodationAndUserReviewPage.getTotalPages(),
                    accommodationAndUserReviewPage.getTotalElements());

            ReviewPageResponseDto responseDto = new ReviewPageResponseDto<>(reviewList, pageDto);

            return responseDto;
        }

        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        List<ReviewDetailResponseDto> responseDtoList = reviewPage.getContent().stream()
                .map(review -> new ReviewDetailResponseDto(
                        review.getId(),
                        new UserDto(review.getUser().getId(), review.getUser().getNickname()),
                        new AccommodationDto(review.getAccommodation().getId(), review.getAccommodation().getAccommodationName()),
                        review.getRating(),
                        review.getContent(),
                        review.getImageUrl(),
                        review.getCreatedAt()
                ))
                .collect(Collectors.toList());

        PageDto pageDto = new PageDto(reviewPage.getNumber() + 1,
                reviewPage.getSize(),
                reviewPage.getTotalPages(),
                reviewPage.getTotalElements());

        ReviewPageResponseDto responseDto = new ReviewPageResponseDto<>(responseDtoList, pageDto);
        return responseDto;

    }

    /**
     * 후기 수정 기능
     *
     * @param reviewUpdateRequestDto
     * @param reviewId
     * @return
     */
    @Transactional
    public ReviewUpdateResponseDto updateReviewService(ReviewUpdateRequestDto reviewUpdateRequestDto, Long reviewId, Long userPrincipalId) {

        // 데이터 준비
        Review review = reviewRepository.findByUserIdAndId(userPrincipalId, reviewId).orElseThrow(() -> new CustomException(ErrorType.REVIEW_NOT_FOUND_OR_FORBIDDEN));

        User user = review.getUser();
        String userNickname = user.getNickname();

        Review updatedReview = review.update(
                reviewUpdateRequestDto.getRating(),
                reviewUpdateRequestDto.getContent(),
                reviewUpdateRequestDto.getImageUrl()
        );

        reviewRepository.saveAndFlush(updatedReview);

        ReviewUpdateResponseDto responseDto = new ReviewUpdateResponseDto(
                reviewId, userNickname, review.getRating(), review.getContent(), review.getImageUrl(), review.getCreatedAt(), updatedReview.getModifiedAt()
        );

        return responseDto;

    }

    /**
     * 후기 삭제 기능
     *
     * @param reviewId
     */
    @Transactional
    public void deleteReviewService(Long reviewId, Long userPrincipalId) {

        // 데이터 준비
        Review review = reviewRepository.findByUserIdAndId(userPrincipalId, reviewId)
                .orElseThrow(() -> new CustomException(ErrorType.REVIEW_NOT_FOUND_OR_FORBIDDEN));

        reviewRepository.delete(review);
    }

}
