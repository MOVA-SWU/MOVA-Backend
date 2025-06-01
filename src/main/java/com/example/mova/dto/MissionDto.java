package com.example.mova.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MissionDto {
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
}
