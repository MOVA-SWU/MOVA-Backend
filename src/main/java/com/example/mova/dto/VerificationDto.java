package com.example.mova.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class VerificationDto {

    @Getter
    @Setter
    public static class checkRequestDto{
        @NotBlank(message = "사진을 기입해주세요.")
        private String name;
        private MultipartFile image;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class checkResponseDto{
        private String checkedUrl;

        private String key;
    }

}
