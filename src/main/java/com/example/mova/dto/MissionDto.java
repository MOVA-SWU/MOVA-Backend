package com.example.mova.dto;

import com.example.mova.enums.MissionStatus;
import lombok.*;

@Data
public class MissionDto {
    //영화기록에서 조회되는 AI 미션
    @Getter
    @Builder
    @NoArgsConstructor
    public static class AiMissionInquire{
        private Long myMissionId;
        private String mission;
        private Integer cost;
        private String characterImage;
        private MissionStatus missionStatus;

        public AiMissionInquire(Long myMissionId, String mission, Integer cost, String characterImage, MissionStatus missionStatus) {
            this.myMissionId = myMissionId;
            this.mission = mission;
            this.cost = cost;
            this.characterImage = characterImage;
            this.missionStatus = missionStatus;
        }
    }
}
