package com.example.mova.dto;

import com.example.mova.enums.Category;
import com.example.mova.enums.MissionStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class sendImageFromAi{
        private MultipartFile image;
        private String mission;
        private String url;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class receiveFromAi{
        private String result;
    }

}
