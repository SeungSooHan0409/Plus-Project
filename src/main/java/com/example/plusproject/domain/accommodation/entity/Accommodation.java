package com.example.plusproject.domain.accommodation.entity;

import com.example.plusproject.common.entity.BaseTimeEntity;
import com.example.plusproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Accommodation extends BaseTimeEntity {
    // createdAt, updatedAt 필드 직접 선언하지 않아도 자동으로 포함됨

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accommodationName;
    private String address;
    private String city;
    private String description;
    private String roomType;
    private String image;
    private String services;
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Accommodation(String accommodationName, String address, String city, String image, String description, String roomType, String services, double price, User user) {
        this.accommodationName = accommodationName;
        this.address = address;
        this.city = city;
        this.description = description;
        this.roomType = roomType;
        this.image = image;
        this.services = services;
        this.price = price;
        this.user = user;
    }
}
