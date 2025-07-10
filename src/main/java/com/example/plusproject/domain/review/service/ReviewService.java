package com.example.plusproject.domain.review.service;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.reservation.entity.Reservation;
import com.example.plusproject.domain.reservation.service.ReservationService;
import com.example.plusproject.domain.review.dto.*;
import com.example.plusproject.domain.review.entity.Review;
import com.example.plusproject.domain.review.repository.ReviewRepository;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// 지저분한거 신경쓰지마세요
// 돌아가는지가 중요해요
// 돌아간다면, 예외 상황도 잘 처리하고 있는지가 중요해요
// 이 코드를 나중에 수정해야해서 읽거나 파악할떄 어려운가? -> 코드를 조금 깔끔하게 적어야겠네
// 코드를 깔끔하게 적는 방법에대해 공부
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ReservationService reservationService;

    // 후기 작성 기능
    public ReviewCreateResponseDto createReviewService(ReviewCreateRequestDto reviewCreateRequestDto) {

        // 데이터 준비
        Double rating = reviewCreateRequestDto.getRating();
        String content = reviewCreateRequestDto.getContent();
        String imageUrl = reviewCreateRequestDto.getImageUrl();
        Long reservationId = reviewCreateRequestDto.getReservationId();

        //
        Reservation reservation = reservationService.findReservationById(reservationId);
        User reservationUser = reservation.getUser();

        // 검증로직 작성

        // 엔티티 만들기
        Review review = new Review(rating, content, imageUrl, reservationUser, reservation);

        // 저장
        Review savedReview = reviewRepository.save(review); // insert

        // responseDto 만들기
        Long savedReviewId = savedReview.getId();
        User savedReviewUser = savedReview.getUser();
        String savedReviewUserNickname = savedReviewUser.getNickname();
        String savedReviewContent = savedReview.getContent();
        LocalDateTime savedReviewCreatedAt = savedReview.getCreatedAt();

        ReviewCreateResponseDto responseDto = new ReviewCreateResponseDto(savedReviewId, savedReviewUserNickname, savedReviewContent, savedReviewCreatedAt);
        // responseDto 반환
        return responseDto;
    }

    // 후기 단건 조회 기능
    public ReviewDetailResponseDto getReviewDetailService(Long reviewId) {

        // 데이터 준비
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);

        // 검증 로직
        if(reviewOptional.isPresent()) {
            Review review = reviewOptional.get();

            User reviewUser = review.getUser();
            Long reviewUserId = reviewUser.getId();
            String reviewUserNickname = reviewUser.getNickname();
            Accommodation reviewAccommodation = review.getAccommodation();
            Long reviewAccommodationId = reviewAccommodation.getId();
            String reviewAccommodationAccommodationName = reviewAccommodation.getAccommodationName();
            Double reviewRating = review.getRating();
            String reviewContent = review.getContent();
            String reviewImageUrl = review.getImageUrl();
            LocalDateTime reviewCreatedAt = review.getCreatedAt();

            UserDto userDto = new UserDto(reviewUserId, reviewUserNickname);
            AccommodationDto accommodationDto = new AccommodationDto(reviewAccommodationId, reviewAccommodationAccommodationName);

            ReviewDetailResponseDto responseDto = new ReviewDetailResponseDto(reviewId, userDto, accommodationDto, reviewRating, reviewContent, reviewImageUrl, reviewCreatedAt);
            return responseDto;

            // TODO ReviewNotFoundException로 교체할것
        } throw new IllegalArgumentException("존재하지 않는 후기입니다.");
    }

    // 후기 전체 조회 기능
    public void getReviewListService() {

        // 데이터 준비
        List<Review> reviewList = reviewRepository.findAll();

    }

    // 후기 수정 기능
    @Transactional
    public void updateReviewService(ReviewUpdateRequestDto reviewUpdateRequestDto, Long reviewId) {
        // Transaction transaction = transactionManager.getTransaction()
        // transaction.start();

        // 데이터 준비
        // 조회된 리뷰
        // id : 1번
        // rating : 5
        // content: 너무 좋았어요
        // imageUrl : cdn.reviewimage.com

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        review.update(
                reviewUpdateRequestDto.getRating(),
                reviewUpdateRequestDto.getContent(), // 기존값을 유지하고 싶은 경우
                reviewUpdateRequestDto.getImageUrl()
        );
        // id : 1번
        // rating : 4
        // content: 너무 너무 좋았어요
        // imageUrl : cdn.reviewimage.com

        // transaction.commit();
        // 쓰기지연 저장소:
        // 어떤 쿼리가 저장 되냐면 1번리뷰의 내용이 "편했어요",
        // 1번 리뷰의 내용이 "다시 생각해보니 정말 편안했어요"
        // 최초에 DB에서 조회한 리뷰의 내용이랑, 트랜잭션 범위내에서 리뷰의 내용이랑 다르구나
        // 다른걸 DB에 반영해줘야겠네 -> update 쿼리를 쓰기지연저장소에 생성해야겠네
        // 트랜잭션 종료 시점에 DB한테 쿼리를 실행해야겠따 -> flush
    } // 트랜잭션 종료 시점 -> flush: 쓰기지연 저장소에 있는 update 쿼리를 DB 반영
    // 조회한 리뷰랑, 트랜잭션이 끝나는 시점에 리뷰가 다르면, 변경된사항들을 DB에 동기화 = 변경 감지(Dirty checking)

    // 후기 삭제 기능
    public void deleteReviewService(Long reviewId) {}

}
