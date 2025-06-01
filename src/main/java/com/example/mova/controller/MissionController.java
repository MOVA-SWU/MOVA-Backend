package com.example.mova.controller;

import com.example.mova.dto.MissionDto;
import com.example.mova.dto.MyMissionDto;
import com.example.mova.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie-records/{movieRecordId}/missions")
public class MissionController {

    private final MissionService missionService;

    //영화 기록글 내에서 조회되는 AI 미션
    @GetMapping
    public ResponseEntity<MissionDto.AiMissionInquire> getAiContent(@PathVariable Long movieRecordId){
        MissionDto.AiMissionInquire aiMission = missionService.findAiMission(movieRecordId);
        return ResponseEntity.ok(aiMission);
    }


    @PatchMapping("/{missionId}/complete")
    public ResponseEntity<String> completeMission(
            @PathVariable Long movieRecordId, @PathVariable Long myissionId, @RequestBody MyMissionDto.myMissionStatusChangeDto request){
        missionService.changeStatus(movieRecordId, myissionId, request);
        return ResponseEntity
                .ok("미션을 수행하셨습니다."); //반환을 메세지로 함.
    }
}
