package com.example.mova.dto;

import lombok.*;

import java.util.List;

//사용자의 캐릭터 수집 현황 조회
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectingDto {
    private int imageCount;
    private List<String> imageUrls;
}
