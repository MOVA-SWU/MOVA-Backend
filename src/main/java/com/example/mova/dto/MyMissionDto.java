package com.example.mova.dto;

import com.example.mova.domain.Mission;
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
        private Long movieRecordId;
        private String mission;
        private Integer cost;

        public myMissionResponseDto(Mission missionEntity) {
            this.mission = missionEntity.getMission(); // 미션 이름
            this.cost = missionEntity.getCost();       // 비용
        }
    }


    //Ai 미션 완료버튼 누르기
    @Getter
    @Setter
    public static class myMissionStatusChangeDto{
        private MissionStatus missionStatus;
    }


}
