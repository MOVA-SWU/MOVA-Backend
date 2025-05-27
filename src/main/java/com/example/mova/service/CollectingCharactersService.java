package com.example.mova.service;

import com.example.mova.domain.CollectingCharacters;
import com.example.mova.dto.CollectingDto;
import com.example.mova.repository.CollectingCharactersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectingCharactersService {

    private final CollectingCharactersRepository collecting;

    public CollectingDto getMyCharacters(long userId){
        //1) DB에서 유저가 수집한 캐릭터 리스트 조회
        List<CollectingCharacters> entities = collecting.findByUserId(userId);

        //2) 캐릭터 이미지 URL만 꺼내서 리스트로
        List<String> urls = entities.stream()
                .map(cc -> cc.getCharacter().getImageUrl())
                .toList();

        //3) DTO에 총 개수와 URL 리스트 담아서 반환
        return CollectingDto.builder()
                .imageCount(urls.size())
                .imageUrls(urls)
                .build();
    }
}
