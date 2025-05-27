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

    //영화기록에서 조회되는 AI 미션
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AiMissionInquire{
        //private Long myMissionId;
        private String mission;
        private Integer cost;
        private String characterImage;
    }

    //Ai 미션 완료버튼 누르기
    @Getter
    public static class myMissionStatusChangeDto{
        private Long myMissionId;
        private MissionStatus missionStatus;

    }


}
