package com.example.mova.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Access;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


public class MovieRecordDto {
    @Getter
    @Setter
    public static class MovieRecordRequestDto{
        @NotBlank(message = "제목을 작성해주세요")
        private String title;

        @NotNull(message = "별점을 표시해주세요")
        @Min(value = 1, message = "별점은 1 이상이어야 합니다")
        @Max(value = 5, message = "별점은 5 이하여야 합니다")
        private Double rating;

        @NotNull(message = "날짜를 설정해주세요")
        @JsonFormat(pattern = "yyyy.MM.dd")
        private LocalDate dateTime;

        @NotBlank(message = "내용을 작성해주세요")
        private String content;
        private String imageUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoiveRecordResponseDto{
        private Long movieId;
        private String title;
        private Double rating;
        private LocalDate dateTime;
        private String content;
        private String imageUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieListResponseDto{
        private Long movieId;
        private String imageUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieLatestResponseDto{
        private Long movieId;
        private String imageUrl;
    }
}
