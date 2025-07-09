package com.example.plusproject.domain.user.service;

import com.example.plusproject.config.JwtUtil;
import com.example.plusproject.domain.user.dto.AuthResponseDto;
import com.example.plusproject.domain.user.dto.LoginRequestDto;
import com.example.plusproject.domain.user.dto.LoginResponseDto;
import com.example.plusproject.domain.user.dto.SignUpRequestDto;
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
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponseDto signup(SignUpRequestDto signUpRequestDto){

        // nickname 중복 확인
        if(userRepository.existsByNickname(signUpRequestDto.getNickname())){
                throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);}

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        // Dto -> Entity
        User user = UserMapper.signUpRequestToUser(signUpRequestDto, encodedPassword);

        User savedUser = userRepository.save(user);

        // ResponseBody data
        return UserMapper.data(savedUser);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        // 존재하는 유저인지
        User user = userRepository.findByNickname(loginRequestDto.getNickname()).orElseThrow(
                () -> new CustomException(ErrorCode.NONEXISTENT_USER));

        // 비밀번호 일치하지 않을 때
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getNickname(), user.getRole());

        return new LoginResponseDto(bearerToken);
    }
}
