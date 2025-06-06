package com.example.mova.dto;

import lombok.*;

import java.util.List;

public class SupportDto {

    //후원 회사 리스트
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompanyListDto{
        private Long companyId;
        private String name;
    }


    // 후원 회사 상세 페이지
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompanySupportDto{
        private String bannerImage;
        private String explainText;
        private List<String> productionImages;
        private Integer supportCost;
    }

    //후원하기
    @Getter
    @Setter
    private static class SupportRequestDto{
        private Boolean supportStatus;

    }



}
