package com.example.mova.controller;

import com.example.mova.dto.PointSumDto;
import com.example.mova.dto.UserDto;
import com.example.mova.service.PointService;
import com.example.mova.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PointService pointService;

    @PostMapping("/signup")
    //빈값을 넣으면 MethodArgumentNotValidException이 발생하도록 추가
    public ResponseEntity<UserDto.UserSignupResponseDto> signup(@Valid @RequestBody UserDto.UserSignupRequestDto request){
        UserDto.UserSignupResponseDto signupResponse = userService.signup(request);
        return ResponseEntity.ok().body(signupResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto.UserLoginResponse> login(@Valid@RequestBody UserDto.UserLoginRequestDto requset){
        UserDto.UserLoginResponse loginResponse = userService.signIn(requset);
        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/emailCheck")
    public ResponseEntity<UserDto.EmailCheckResponseDto> checkEmail(
            @Valid @RequestBody UserDto.EmailCheckRequestDto request){

        boolean isDuplicated = userService.checkEmailDuplicated(request.getEmail());
        return ResponseEntity.ok(new UserDto.EmailCheckResponseDto(isDuplicated));

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "로그아웃 성공";
    }

    @GetMapping("/{userId}/pointSum")
    public ResponseEntity<PointSumDto> getPointSum(@PathVariable Long userId){
        return ResponseEntity.ok(pointService.getPointSum(userId));
    }


}
