package com.example.plusproject.domain.user.entity;

import com.example.plusproject.common.entity.BaseTimeEntity;
import com.example.plusproject.domain.user.constraint.ValidPassword;
import com.example.plusproject.domain.user.constraint.ValidPhoneNumber;
import com.example.plusproject.domain.user.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String nickname;

    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    @ValidPassword
    private String password;

    @NotBlank
    @ValidPhoneNumber
    private String phoneNumber;

    @NotBlank
    private String residence;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt = null;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role = UserRole.GUEST;

    public User(String name, String nickname, String email, String password, String phoneNumber, String residence, UserRole role) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.residence = residence;
        this.role = role;
    }

    public void update(UserRole userRole){
        this.role = userRole;
    }
    public void delete(){this.deletedAt = LocalDateTime.now();}
}
