package com.example.mova.controller;

import com.example.mova.dto.MyMissionDto;
import com.example.mova.enums.MissionStatus;
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

    @GetMapping("/myMissions")
    public ResponseEntity<List<MyMissionDto.myMissionResponseDto>> listByStatus(
            @RequestParam(value = "status", defaultValue = "AVAILABLE") MissionStatus missionStatus) {

        List<MyMissionDto.myMissionResponseDto> missionList;

        switch (missionStatus) {
            case AVAILABLE -> missionList = myMissionService.listByStatus(MissionStatus.AVAILABLE);
            case COMPLETED -> missionList = myMissionService.listByStatus(MissionStatus.COMPLETED);
            default -> throw new IllegalArgumentException("해당 상태를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(missionList);
    }


}
