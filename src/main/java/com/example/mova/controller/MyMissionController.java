package com.example.mova.controller;

import com.example.mova.dto.AiTaskDto;
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
            @RequestParam(value = "status", required = false) MissionStatus missionStatus){

        List<MyMissionDto.myMissionResponseDto> missionList;

        switch (missionStatus) {
            case AVAILABLE -> missionList = myMissionService.listByStatus(MissionStatus.AVAILABLE);
            case COMPLETED -> missionList = myMissionService.listByStatus(MissionStatus.COMPLETED);
            default -> throw new IllegalArgumentException(("해당 상태를 찾을 수 없습니다."));
        }
       return ResponseEntity.ok(missionList);
    }


    //영화 기록글 내에서 조회되는 AI 미션
    @GetMapping("/movie-records/{movieRecordId}/missions")
    public ResponseEntity<MyMissionDto.AiMissionInquire> getAiContent(@PathVariable Long movieRecordId){
        MyMissionDto.AiMissionInquire aiMission = myMissionService.findAiMission(movieRecordId);
        return ResponseEntity.ok(aiMission);
    }

    @PatchMapping("/movie-records/{movieRecordId}/missions/{myMissionId}/complete")
    public ResponseEntity<String> completeMission(
            @PathVariable Long movieRecordId, @PathVariable Long myMissionId){
        myMissionService.changeStatus(movieRecordId, myMissionId);
        return ResponseEntity
                .ok("미션을 수행하셨습니다."); //반환을 메세지로 함.
    }


}
