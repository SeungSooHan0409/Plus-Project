package com.example.plusproject.domain.user.repository;

import com.example.plusproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);
    boolean existsByNickname(String nickname);


}
