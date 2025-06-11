package com.example.mova.service;

import com.example.mova.domain.MyMission;
import com.example.mova.dto.MyMissionDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.errorhandler.ApiExceptions;
import com.example.mova.repository.MyMissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MyMissionService {
    private final MyMissionRepository myMissionRepository;


    //상태에 따라 미션들 조회하기
    public List<MyMissionDto.myMissionResponseDto> listByStatus(Long userId, MissionStatus missionStatus) {
        List<MyMission> entities = myMissionRepository.findAllByUser_IdAndMissionStatus(userId, missionStatus);
        return entities.stream()
                .map(m -> MyMissionDto.myMissionResponseDto.builder()
                        .myMissionId(m.getMyMissionId())
                        .movieRecordId( m.getMission().getMovieRecord().getId())
                        .mission(m.getMission().getMission())
                        .cost(m.getMission().getCost())
                        .build()
                )
                .collect(Collectors.toList());


        /*return myMissionRepository.findAllByMission_MissionStatus(missionStatus).stream()
                .map(this ::toListDto)
                .collect(Collectors.toList());
    }

    private MyMissionDto.myMissionResponseDto toListDto(MyMission myMission){
        return MyMissionDto.myMissionResponseDto.builder()
                .myMissionId(myMission.getMyMissionId())
                .mission(myMission.getMission().getMission())
                .cost(myMission.getMission().getCost())
                .build();
    } */
    }

    //AI 미션 상태를 완료로 변환하는 코드
    @Transactional
    public void completeMyMission(
            Long movieRecordId,
            Long myMissionId,
            Long userId) {

        MyMission myMission = myMissionRepository
                .findByMyMissionIdAndUser_IdAndMission_MovieRecord_Id(
                        myMissionId, userId, movieRecordId)
                .orElseThrow(() -> new ApiExceptions.MyMissionNotFoundException(myMissionId));

        myMission.updateStatus(MissionStatus.COMPLETED);
    }

}
