package com.example.plusproject.domain.user.entity;

import com.example.plusproject.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String residence;
    private String role;

    public User(String name, String nickname, String email, String password, String phoneNumber, String residence, String role) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.residence = residence;
        this.role = role;
    }
}
