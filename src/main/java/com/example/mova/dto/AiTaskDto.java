package com.example.mova.dto;

import com.example.mova.enums.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AiTaskDto {

    @Getter
    public static class RequestToAi{
        private String title;
    }

    @Getter
    @Setter
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
