package com.example.mova.controller;

import com.example.mova.config.JWTUtil;
import com.example.mova.domain.User;
import com.example.mova.dto.MissionDto;
import com.example.mova.repository.UserRepository;
import com.example.mova.service.MissionService;
import com.example.mova.service.MyMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie-records/{movieRecordId}/missions")
public class MissionController {

    private final MissionService missionService;
    private final MyMissionService myMissionService;
    private final UserRepository userRepository;

    //영화 기록글 내에서 조회되는 AI 미션
    @GetMapping
    public ResponseEntity<MissionDto.AiMissionInquire> getAiContent(@PathVariable Long movieRecordId
    ){
        String email = JWTUtil.getCurrentUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다." + email));
        MissionDto.AiMissionInquire aiMission = missionService.findAiMission(movieRecordId, user.getId());
        return ResponseEntity.ok(aiMission);
    }


    //사진 검증 때문에 필요 X
    /*@PatchMapping("/{myMissionId}/complete")
    public ResponseEntity<String> completeMission(
            @PathVariable Long movieRecordId,
            @PathVariable Long myMissionId) {
        String email = JWTUtil.getCurrentUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다." + email));

        myMissionService.completeMyMission(movieRecordId, myMissionId, user.getId());
        return ResponseEntity.ok("미션을 수행하셨습니다.");
    }
     */
}
