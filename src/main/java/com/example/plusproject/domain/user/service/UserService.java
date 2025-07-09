package com.example.plusproject.domain.user.service;

import com.example.plusproject.config.CustomUserPrincipal;
import com.example.plusproject.domain.user.dto.AuthResponseDto;
import com.example.plusproject.domain.user.dto.ChangeRoleDto;
import com.example.plusproject.domain.user.dto.DeleteUserRequestDto;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.entity.UserMapper;
import com.example.plusproject.domain.user.repository.UserRepository;
import com.example.plusproject.exception.CustomException;
import com.example.plusproject.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // id로 엔티티 반환
    public User findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NONEXISTENT_USER));

        if (user.getDeletedAt() != null) {
            return user;
        }
        throw new CustomException(ErrorCode.NONEXISTENT_USER);
    }

    @Transactional
    public ChangeRoleDto changeRole(Long id, ChangeRoleDto changeRoleDto) {

        // 존재하는 유저인지
        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NONEXISTENT_USER));
        if (user.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.NONEXISTENT_USER);
        }

        // 원래 Role과 다른지 확인
        if (!user.getRole().equals(changeRoleDto.getRole())) {
            // Role 변경
            user.update(changeRoleDto.getRole());
        } else throw new CustomException(ErrorCode.SAME_ROLE);

        return new ChangeRoleDto(changeRoleDto.getRole());
    }

    public AuthResponseDto getUser(CustomUserPrincipal customUserPrincipal) {
        User user = userRepository.findById(customUserPrincipal.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.NONEXISTENT_USER));

        // Entity -> dto
        return UserMapper.data(user);
    }

    @Transactional
    public void deleteUser(Long id, DeleteUserRequestDto inputPassword, Long currentUserId) {

        User user = userRepository.findById(currentUserId).orElseThrow(
                () -> new CustomException(ErrorCode.NONEXISTENT_USER));

        // 비밀번호 확인과 현재 로그인한 유저가 지우려는 유저가 맞다면 유저 삭제 (soft delete)
        if (passwordEncoder.matches(inputPassword.getInputPassword(), user.getPassword())) {
            if (currentUserId.equals(id)) {
                user.delete();
            } else throw new CustomException(ErrorCode.NO_ACCESS);
        } else throw new CustomException(ErrorCode.INVALID_PASSWORD);

    }
}

