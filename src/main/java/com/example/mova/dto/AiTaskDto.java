package com.example.mova.dto;

import com.example.mova.enums.MissionStatus;
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
