package com.example.plusproject.domain.accommodation.entity;

import com.example.plusproject.common.entity.BaseTimeEntity;
import com.example.plusproject.common.exception.CustomException;
import com.example.plusproject.common.exception.ErrorType;
import com.example.plusproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


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

    public void changeAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeCity(String city) {
        this.city = city;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void changeImage(String image) {
        this.image = image;
    }

    public void changeServices(String services) {
        this.services = services;
    }

    public void changePrice(double price) {
        if(price <= 0) {
            throw new CustomException(ErrorType.INVALID_PRICE);
        }
        this.price = price;
    }
}
