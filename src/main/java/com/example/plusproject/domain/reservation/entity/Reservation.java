package com.example.plusproject.domain.reservation.entity;

import com.example.plusproject.common.entity.BaseTimeEntity;
import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.reservation.dto.ReservationData;
import com.example.plusproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table
@NoArgsConstructor
@Setter
public class Reservation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long guestCount;
    private LocalDate checkInDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;


    public Reservation(Accommodation accommodation, User user, LocalDate checkInDate, Long guestCount) {
        this.accommodation = accommodation;
        this.user = user;
        this.checkInDate = checkInDate;
        this.guestCount = guestCount;
    }


    public static ReservationData toData(Reservation reservation) {
        return new ReservationData(
                reservation.getId(),
                reservation.getAccommodation().getAddress(),
                reservation.getGuestCount(),
                reservation.getCheckInDate());
    }
}
