package com.example.plusproject.domain.user.controller;

import com.example.plusproject.common.dto.ApiResponseDto;
import com.example.plusproject.config.CustomUserPrincipal;
import com.example.plusproject.domain.user.dto.AuthResponseDto;
import com.example.plusproject.domain.user.dto.ChangeRoleDto;
import com.example.plusproject.domain.user.dto.DeleteUserRequestDto;
import com.example.plusproject.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    // 사용자 역할 변경
    @PutMapping("/api/users/{id}")
    public ResponseEntity<ApiResponseDto> changeRole(@PathVariable Long id, @Valid @RequestBody ChangeRoleDto changeRoleDto){

        ChangeRoleDto changeRole = userService.changeRole(id, changeRoleDto);

        ApiResponseDto success = ApiResponseDto.success("역할이 변경되었습니다.", changeRole);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    // 사용자 조회
    @GetMapping("/api/users/me")
    public ResponseEntity<ApiResponseDto> getUser(@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal){

        AuthResponseDto currentUser = userService.getUser(customUserPrincipal);

        ApiResponseDto success = ApiResponseDto.success("현재사용자 조회에 성공했습니다.", currentUser);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }


    // 사용자 삭제
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Long id, @RequestBody DeleteUserRequestDto inputPassword, @AuthenticationPrincipal CustomUserPrincipal currentUser){

        userService.deleteUser(id, inputPassword, currentUser.getId() );

        ApiResponseDto success = ApiResponseDto.success("유저가 삭제되었습니다.", null);

        return ResponseEntity.status(HttpStatus.OK).body(success);

    }
}
