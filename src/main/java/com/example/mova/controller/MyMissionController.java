package com.example.mova.controller;

import com.example.mova.domain.User;
import com.example.mova.dto.MyMissionDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.repository.UserRepository;
import com.example.mova.service.MyMissionService;
import com.example.mova.config.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyMissionController {
    private final MyMissionService myMissionService;
    private final UserRepository userRepository;  // UserRepository 주입

    @GetMapping("/myMissions")
    public ResponseEntity<List<MyMissionDto.myMissionResponseDto>> listByStatus(
            @RequestParam(value = "status", defaultValue = "AVAILABLE") MissionStatus missionStatus) {

        System.out.println("[MyMissionController] 넘어온 missionStatus 파라미터: " + missionStatus);

        // 1. JWT에서 email 꺼내기
        String email = JWTUtil.getCurrentUsername();
        // 2. email로 User 조회 → userId 확보
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. email=" + email));
        Long userId = user.getId();

        List<MyMissionDto.myMissionResponseDto> missionList;
        switch (missionStatus) {
            case AVAILABLE -> {
                System.out.println("상태: AVAILABLE - myMissionService 사용");
                missionList = myMissionService.listByStatus(userId, MissionStatus.AVAILABLE);
            }
            case COMPLETED -> {
                System.out.println("상태: COMPLETED - myMissionService 사용");
                missionList = myMissionService.listByStatus(userId, MissionStatus.COMPLETED);
            }
            default -> throw new IllegalArgumentException("해당 상태를 찾을 수 없습니다.");
        }

        System.out.println("변환된 missionStatus: " + missionStatus);
        return ResponseEntity.ok(missionList);
    }
}
