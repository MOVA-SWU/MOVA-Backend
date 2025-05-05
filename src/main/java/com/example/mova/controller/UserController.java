package com.example.mova.controller;

import com.example.mova.dto.UserDto;
import com.example.mova.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    //빈값을 넣으면 MethodArgumentNotValidException이 발생하도록 추가
    public ResponseEntity<UserDto.UserSignupResponseDto> signup(@Valid @RequestBody UserDto.UserSignupRequestDto request){
        UserDto.UserSignupResponseDto signupResponse = userService.signup(request);
        return ResponseEntity.ok().body(signupResponse);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserDto.UserLoginResponse> login(@Valid@RequestBody UserDto.UserLoginRequestDto requset){
        UserDto.UserLoginResponse loginResponse = userService.signIn(requset);
        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/users/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "로그아웃 성공";
    }


}
