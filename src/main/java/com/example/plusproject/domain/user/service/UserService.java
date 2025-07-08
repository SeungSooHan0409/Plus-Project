package com.example.plusproject.domain.user.service;

import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.repository.UserRepository;
import com.example.plusproject.exception.CustomException;
import com.example.plusproject.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // id로 엔티티 반환
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.NONEXISTENT_USER));
    }
}
