package com.example.plusproject.domain.user.controller;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.domain.user.dto.AuthResponseDto;
import com.example.plusproject.domain.user.dto.LoginRequestDto;
import com.example.plusproject.domain.user.dto.LoginResponseDto;
import com.example.plusproject.domain.user.dto.SignUpRequestDto;
import com.example.plusproject.domain.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto){

        AuthResponseDto signUp = authService.signup(signUpRequestDto);

        ApiResponseDto success = ApiResponseDto.success("회원가입이 성공했습니다.", signUp);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<ApiResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto){

        LoginResponseDto login = authService.login(loginRequestDto);

        ApiResponseDto success = ApiResponseDto.success("로그인에 성공했습니다.", login);

        return ResponseEntity.status(HttpStatus.OK).body(success);

    }
}
