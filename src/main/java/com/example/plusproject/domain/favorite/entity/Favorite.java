package com.example.plusproject.domain.favorite.entity;


import com.example.plusproject.domain.accommodation.entity.Accommodation;
import com.example.plusproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Favorite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public Favorite(User user, Accommodation accommodation) {
        this.user = user;
        this.accommodation = accommodation;
    }
}
