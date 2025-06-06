package com.example.mova.controller;

import com.example.mova.dto.MyMissionDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.service.MissionService;
import com.example.mova.service.MyMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.mova.enums.MissionStatus.AVAILABLE;

@RestController
@RequiredArgsConstructor
public class MyMissionController {
    private final MyMissionService myMissionService;
    private final MissionService missionService;

    /*@GetMapping("/myMissions")
    public ResponseEntity<List<MyMissionDto.myMissionResponseDto>> listByStatus(
            @RequestParam(value = "status", required = false) MissionStatus missionStatus) {

        if (missionStatus == null) {
            missionStatus = MissionStatus.AVAILABLE; // 기본값 설정
        }

        List<MyMissionDto.myMissionResponseDto> missionList;

        switch (missionStatus) {
            case AVAILABLE -> missionList = myMissionService.listByStatus(MissionStatus.AVAILABLE);
            case COMPLETED -> missionList = missionService.listByStatus(MissionStatus.COMPLETED);
            default -> throw new IllegalArgumentException("해당 상태를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(missionList);
    } */

    @GetMapping("/myMissions")
    public ResponseEntity<List<MyMissionDto.myMissionResponseDto>> listByStatus(
            @RequestParam(value = "status", required = false) String status
    ) {
        System.out.println("넘어온 status 파라미터: " + status);

        MissionStatus missionStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                missionStatus = MissionStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("올바르지 않은 미션 상태입니다: " + status);
            }
        }

        System.out.println("변환된 missionStatus: " + missionStatus);

        List<MyMissionDto.myMissionResponseDto> missionList;

        if (missionStatus == MissionStatus.COMPLETED) {
            missionList = missionService.listByStatus(missionStatus);
        } else {
            missionList = myMissionService.listByStatus(missionStatus != null ? missionStatus : MissionStatus.AVAILABLE);
        }

        return ResponseEntity.ok(missionList);
    }



}
