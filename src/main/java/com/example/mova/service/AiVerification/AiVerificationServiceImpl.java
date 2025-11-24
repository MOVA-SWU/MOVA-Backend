package com.example.mova.service.AiVerification;

import com.example.mova.Ai.AiClient;
import com.example.mova.domain.Mission;
import com.example.mova.domain.MyMission;
import com.example.mova.domain.User;
import com.example.mova.dto.AiTaskDto;
import com.example.mova.dto.VerificationDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.repository.*;
import com.example.mova.service.imageservice.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AiVerificationServiceImpl implements AiVerificationService{
    private final MyMissionRepository myMissionRepository;
    private final UserRepository userRepository;
    private final AiClient aiClient;
    private final VerificationService verificationService;

    public AiTaskDto.receiveFromAi verifyImage(Long myMissionId, Long userId, AiTaskDto.sendImageFromAi request){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));

        //MyMission 조회(myMissionId 기준)
        MyMission myMission = myMissionRepository.findById(myMissionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 미션이 존재하지 않습니다."));


        Mission mission = myMission.getMission();
        String missionText = mission.getMission();

        VerificationDto.checkResponseDto uploadResult = verificationService.upload(request.getImage());
        String imageUrl = uploadResult.getCheckedUrl();
        if (imageUrl == null) {
            throw new IllegalStateException("S3 Upload Failed");
        }
        myMission.setCheckedUrl(imageUrl);

        //AI 연동 -> mission 전송
        AiTaskDto.sendImageFromAi fromAi = new AiTaskDto.sendImageFromAi();
        fromAi.setMission(request.getMission());
        fromAi.setUrl(imageUrl);

        AiTaskDto.receiveFromAi result = aiClient.verifyMission(fromAi);

        if ("성공".equals(result.getResult())){
            myMission.setMissionStatus(MissionStatus.COMPLETED);
        }
        else {
            String fileKey = verificationService.extractFileNameFromUrl(imageUrl);
            verificationService.delete(fileKey);
            myMission.setCheckedUrl(null);
            System.out.println("검증 실패로 사진이 삭제됩니다.");
            //myMission.setMissionStatus(MissionStatus.AVAILABLE);
        }
        myMissionRepository.save(myMission);
        return result;
    }
}
