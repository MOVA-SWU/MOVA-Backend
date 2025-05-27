package com.example.mova.controller;

import com.example.mova.domain.User;
import com.example.mova.dto.CollectingDto;
import com.example.mova.dto.UserDto;
import com.example.mova.repository.UserRepository;
import com.example.mova.service.CollectingCharactersService;
import com.example.mova.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {

    private final CollectingCharactersService collectingCharactersService;
    private final UserService userService;
    @GetMapping("/collection-status")
    public ResponseEntity<CollectingDto> myCharacters(
            @AuthenticationPrincipal User principal){

        Long userId = principal.getId();
        CollectingDto list = collectingCharactersService.getMyCharacters(userId);
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<String> getNickName(
            @AuthenticationPrincipal User principal,
            @RequestBody @Valid UserDto.NickNameUpdateDto request){
        userService.updateNickname(principal.getId(), request.getNickname());

        return ResponseEntity.ok("닉네임이 수정되었습니다.");
    }

}
