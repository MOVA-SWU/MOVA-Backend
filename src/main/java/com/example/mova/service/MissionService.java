package com.example.mova.service;

import com.example.mova.domain.Mission;
import com.example.mova.dto.MissionDto;
import com.example.mova.dto.MyMissionDto;
import com.example.mova.errorhandler.ApiExceptions;
import com.example.mova.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    //AI 미션 조회하는 서비스 구현 코드
    public MissionDto.AiMissionInquire findAiMission(long movieRecordId) {
        Mission mission = missionRepository
                .findByMovieRecordId(movieRecordId)
                .orElseThrow(() -> new ApiExceptions.MovieRecordNotFoundException(movieRecordId));

        return new MissionDto.AiMissionInquire(
                mission.getMission(),
                mission.getCost(),
                mission.getStoryCharacter().getImageUrl()
        );
    }

    //AI 미션 상태를 완료로 변환하는 코드
    public void changeStatus(
            long movieRecordId,
            long missionId,
            MyMissionDto.myMissionStatusChangeDto request){

         Mission mission = missionRepository.findByMissionIdAndMovieRecordId(missionId, movieRecordId)
                .orElseThrow(() -> new ApiExceptions.MyMissionNotFoundException(missionId));

        mission.update(request.getMissionStatus());

    }
}
