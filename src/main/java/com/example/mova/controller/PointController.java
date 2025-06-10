package com.example.mova.controller;

import com.example.mova.domain.User;
import com.example.mova.dto.PointSumDto;
import com.example.mova.repository.UserRepository;
import com.example.mova.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final UserRepository userRepository;
    private final PointService pointService;
    @GetMapping("/pointSum")
    public ResponseEntity<PointSumDto> getPointSum(Authentication authentication){

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName(); // subject에 해당하는 email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Long userId = user.getId();
        return ResponseEntity.ok(pointService.getPointSum(userId));
    }
}
