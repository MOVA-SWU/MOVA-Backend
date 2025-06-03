package com.example.mova.dto;

import com.example.mova.enums.Category;
import com.example.mova.enums.MissionStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

public class AiTaskDto {

    @Getter
    @Setter
    public static class RequestToAi{
        private String title;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true) //모르는 필드 무시하기
    public static class ResponseFromAi{
        private String movie;
        private String mission;
        private String effect;
        private String point_message;
        private Integer point;
        private String theme;
        private String image_url;
    }

}
