package com.example.mova.service;

import com.example.mova.domain.Mission;
import com.example.mova.dto.MissionDto;
import com.example.mova.dto.MyMissionDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.errorhandler.ApiExceptions;
import com.example.mova.repository.MissionRepository;
import com.example.mova.repository.MyMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final MyMissionRepository myMissionRepository;
    //AI 미션 조회하는 서비스 구현 코드
    @Transactional
    public MissionDto.AiMissionInquire findAiMission(long movieRecordId) {
        Mission mission = missionRepository
                .findByMovieRecordId(movieRecordId)
                .orElseThrow(() -> new ApiExceptions.MovieRecordNotFoundException(movieRecordId));

        return new MissionDto.AiMissionInquire(
                mission.getMission(),
                mission.getCost(),
                mission.getCharacter()
        );
    }

    //AI 미션 상태를 완료로 변환하는 코드
    @Transactional
    public void changeStatus(
            long movieRecordId,
            long missionId,
            MyMissionDto.myMissionStatusChangeDto request){

         Mission mission = missionRepository.findByMissionIdAndMovieRecordId(missionId, movieRecordId)
                .orElseThrow(() -> new ApiExceptions.MyMissionNotFoundException(missionId));

        mission.update(request.getMissionStatus());
    }


    //미션 상태가 COMPLETED 일 때
    /*public List<MyMissionDto.myMissionResponseDto> listByStatus(MissionStatus missionStatus) {
        List<Mission> missionList = missionRepository.findByMissionStatus(missionStatus);

        return missionList.stream()
                .map(MyMissionDto.myMissionResponseDto::new)
                .collect(Collectors.toList());
    } */

    public List<MyMissionDto.myMissionResponseDto> listByStatus(MissionStatus missionStatus) {
        System.out.println(">>> MissionService: 전달받은 missionStatus = " + missionStatus);

        List<Mission> missionList = missionRepository.findByMissionStatus(missionStatus);

        System.out.println(">>> missionRepository 결과 개수: " + missionList.size());

        return missionList.stream()
                .map(MyMissionDto.myMissionResponseDto::new)
                .collect(Collectors.toList());
    }

}
