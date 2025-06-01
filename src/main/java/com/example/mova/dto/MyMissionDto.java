package com.example.mova.dto;

import com.example.mova.enums.MissionStatus;
import lombok.*;

public class MyMissionDto {

    //미션 리스트 조회
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class myMissionResponseDto{
        private Long myMissionId;
        private String mission;
        private Integer cost;
    }


    //Ai 미션 완료버튼 누르기
    @Getter
    @Setter
    public static class myMissionStatusChangeDto{
        private MissionStatus missionStatus;
    }


}
