package com.example.plusproject.domain.accommodation.entity;

import com.example.plusproject.common.entity.BaseTimeEntity;
import com.example.plusproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Accommodation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String city;
    private String description;
    private String roomType;
    private String services;
    private double price;
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Accommodation(String address, String city, String description, String roomType, String services, double price, LocalDateTime deletedAt, User user) {
        this.address = address;
        this.city = city;
        this.description = description;
        this.roomType = roomType;
        this.services = services;
        this.price = price;
        this.deletedAt = deletedAt;
        this.user = user;
    }

}
