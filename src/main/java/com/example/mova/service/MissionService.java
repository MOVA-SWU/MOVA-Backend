package com.example.mova.service;

import com.example.mova.domain.Mission;
import com.example.mova.domain.MyMission;
import com.example.mova.dto.MissionDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.errorhandler.ApiExceptions;
import com.example.mova.repository.MissionRepository;
import com.example.mova.repository.MyMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final MyMissionRepository myMissionRepository;

    //AI 미션 조회하는 서비스 구현 코드
    @Transactional
    public MissionDto.AiMissionInquire findAiMission(long movieRecordId, Long userId) {
        Mission mission = missionRepository
                .findByMovieRecordId(movieRecordId)
                .orElseThrow(() -> new ApiExceptions.MovieRecordNotFoundException(movieRecordId));

        // Optional.map 결과를 꺼낼 때 orElse 로 기본값 지정
        MissionStatus myStatus = myMissionRepository
                .findByUser_IdAndMission_MissionId(userId, mission.getMissionId())
                .map(MyMission::getMissionStatus)
                .orElse(MissionStatus.AVAILABLE);

        return new MissionDto.AiMissionInquire(
                mission.getMissionId(),
                mission.getMission(),
                mission.getCost(),
                mission.getCharacter(),
                myStatus
        );
    }

}
