package com.example.plusproject.domain.user.service;

import com.example.plusproject.common.exception.CustomException;
import com.example.plusproject.common.exception.ErrorType;
import com.example.plusproject.domain.user.dto.AuthResponseDto;
import com.example.plusproject.domain.user.dto.AuthUser;
import com.example.plusproject.domain.user.dto.ChangeRoleDto;
import com.example.plusproject.domain.user.entity.User;
import com.example.plusproject.domain.user.entity.UserMapper;
import com.example.plusproject.domain.user.repository.UserRepository;
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
                .orElseThrow(()->new CustomException(ErrorType.NONEXISTENT_USER));
    }

    @Transactional
    public ChangeRoleDto changeRole(Long id, ChangeRoleDto changeRoleDto){

        // 존재하는 유저인지
        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorType.NONEXISTENT_USER));

        // 원래 Role과 다른지 확인
        if (!user.getRole().equals(changeRoleDto.getRole())){
            // Role 변경
            user.update(changeRoleDto.getRole());
        }else throw new CustomException(ErrorType.SAME_ROLE);

        return new ChangeRoleDto(changeRoleDto.getRole());
    }

    public AuthResponseDto getUser(AuthUser authUser){
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new CustomException(ErrorType.NONEXISTENT_USER));

        // Entity -> dto
        return UserMapper.data(user);
    }

}
