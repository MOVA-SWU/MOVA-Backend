package com.example.mova.service;

import com.example.mova.domain.Mission;
import com.example.mova.domain.MovieRecord;
import com.example.mova.domain.MyMission;
import com.example.mova.dto.MyMissionDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.errorhandler.ApiExceptions;
import com.example.mova.repository.MissionRepository;
import com.example.mova.repository.MovieRecordRepository;
import com.example.mova.repository.MyMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.yaml.MappingJackson2YamlHttpMessageConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyMissionService {
    private final MyMissionRepository myMissionRepository;
    private final MissionRepository missionRepository;

    //상태에 따라 미션들 조회하기
    public List<MyMissionDto.myMissionResponseDto> listByStatus(MissionStatus missionStatus){
        return myMissionRepository.findAllByMissionStatus(missionStatus).stream()
                .map(this ::toListDto)
                .collect(Collectors.toList());
    }

    private MyMissionDto.myMissionResponseDto toListDto(MyMission myMission){
        return MyMissionDto.myMissionResponseDto.builder()
                .myMissionId(myMission.getMyMissionId())
                .mission(myMission.getMission().getMission())
                .cost(myMission.getMission().getPoint().getCost())
                .build();
    }

    //AI 미션 조회하는 서비스 구현 코드
    public MyMissionDto.AiMissionInquire findAiMission(long movieRecordId) {
        Mission myMission = missionRepository
                .findByMovieRecordId(movieRecordId)
                .orElseThrow(() -> new ApiExceptions.MovieRecordNotFoundException(movieRecordId));

        return new MyMissionDto.AiMissionInquire.builder()


    }

    //AI 미션 상태를 완료로 변환하는 코드
    public MyMissionDto.myMissionStatusChangeDto changeStatus(long movieRecordId, long myMissionId){
        MyMission mission = myMissionRepository.findByMyMissionIdAndMovieRecordId(myMissionId, movieRecordId)
                .orElseThrow(() -> new ApiExceptions.MyMissionNotFoundException(myMissionId));
    }



}
