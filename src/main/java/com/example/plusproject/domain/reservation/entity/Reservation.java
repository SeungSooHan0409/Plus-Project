package com.example.plusproject.domain.reservation.entity;

import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long guestCount;
    private LocalDate checkInDate;
    private LocalDate cratedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;


    public Reservation(Accommodation accommodation, User user, LocalDate cratedAt, LocalDate checkInDate, Long guestCount) {
        this.accommodation = accommodation;
        this.user = user;
        this.cratedAt = cratedAt;
        this.checkInDate = checkInDate;
        this.guestCount = guestCount;
    }
}
