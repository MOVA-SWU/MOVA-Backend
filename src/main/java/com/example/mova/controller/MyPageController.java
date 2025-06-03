package com.example.mova.controller;

import com.example.mova.domain.User;
import com.example.mova.dto.CollectingDto;
import com.example.mova.dto.UserDto;
import com.example.mova.repository.UserRepository;
import com.example.mova.service.CollectingCharactersService;
import com.example.mova.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {

    private final CollectingCharactersService collectingCharactersService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto.MyPageResponseDto> myPage(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // TokenProvider에서 넣은 principal은 org.springframework.security.core.userdetails.User
        String email = authentication.getName(); // = claims.getSubject()

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        UserDto.MyPageResponseDto response = userService.getMyPage(user.getId());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/collection-status")
    public ResponseEntity<CollectingDto> myCharacters(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName(); // subject에 해당하는 email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Long userId = user.getId();
        CollectingDto list = collectingCharactersService.getMyCharacters(userId);
        return ResponseEntity.ok(list);
    }


    @PatchMapping("/nickname")
    public ResponseEntity<String> getNickName(
            Authentication authentication,
            @RequestBody @Valid UserDto.NickNameUpdateDto request) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증이 필요합니다.");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        userService.updateNickname(user.getId(), request.getNickname());
        return ResponseEntity.ok("닉네임이 수정되었습니다.");
    }


}
