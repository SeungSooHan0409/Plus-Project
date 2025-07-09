package com.example.plusproject.domain.review.entity;

import com.example.plusproject.common.entity.BaseTimeEntity;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.reservation.entity.Reservation;
import com.example.plusproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rating;
    private String content;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;


    public Review(double rating, String content, String imageUrl, User user, Reservation reservation, Accommodation accommodation) {
        this.rating = rating;
        this.content = content;
        this.imageUrl = imageUrl;
        this.user = user;
        this.reservation = reservation;
        this.accommodation = accommodation;
    }

    // 응집도 향상을 위한 도메인 로직 구성
    public void update(double rating, String content, String imageUrl) {
        this.rating = rating;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
