package com.example.mova.controller;

import com.example.mova.dto.MyMissionDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.service.MyMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class MyMissionController {
    private final MyMissionService myMissionService;

    @GetMapping("/myMissions")
    public ResponseEntity<List<MyMissionDto.myMissionResponseDto>> listByStatus(
            @RequestParam(value = "status", defaultValue = "AVAILABLE") MissionStatus missionStatus) {

        System.out.println("[MyMissionController] 넘어온 missionStatus 파라미터: " + missionStatus);

        List<MyMissionDto.myMissionResponseDto> missionList;

        switch (missionStatus) {
            case AVAILABLE -> {
                System.out.println("상태: AVAILABLE - myMissionService 사용");
                missionList = myMissionService.listByStatus(MissionStatus.AVAILABLE);
            }
            case COMPLETED -> {
                System.out.println("상태: COMPLETED - myMissionService 사용");
                missionList = myMissionService.listByStatus(MissionStatus.COMPLETED);
            }
            default -> throw new IllegalArgumentException("해당 상태를 찾을 수 없습니다.");
        }

        System.out.println("변환된 missionStatus: " + missionStatus);

        return ResponseEntity.ok(missionList);
    }

}
