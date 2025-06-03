package com.example.mova.service;

import com.example.mova.Ai.AiClient;
import com.example.mova.domain.*;
import com.example.mova.dto.AiTaskDto;
import com.example.mova.dto.MovieRecordDto;
import com.example.mova.enums.Category;
import com.example.mova.enums.MissionStatus;
import com.example.mova.errorhandler.DuplicateRecordException;
import com.example.mova.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AiAnalyzeService {

    private final MovieRecordRepository movieRecordRepository;
    private final MyMissionRepository myMissionRepository;
    private final MissionRepository missionRepository;
    private final CharacterRepository characterRepository;
    private final CollectingCharactersRepository collectingCharactersRepository;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final AiClient aiClient;

    public AiTaskDto.ResponseFromAi analyzeAndSaveMovieRecord(MovieRecordDto.MovieRecordRequestDto request, String email){

        // 1. 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. 중복 제목 검사
        movieRecordRepository.findByUserAndTitle(user, request.getTitle())
                .ifPresent(existing -> {
                    throw new DuplicateRecordException("이미 등록된 기록입니다. id = " + existing.getId());
                });

        MovieRecord movieRecord = MovieRecord.builder()
                .title(request.getTitle())
                .rating(request.getRating())
                .dateTime(request.getDateTime())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .user(user)
                .build();

        movieRecordRepository.save(movieRecord);

        //AI 연동 - title만 전송
        AiTaskDto.RequestToAi aiRequest = new AiTaskDto.RequestToAi();
        aiRequest.setTitle(request.getTitle());
        AiTaskDto.ResponseFromAi aiResponse = aiClient.sendToAi(aiRequest);

        //AI 응답 결과를 각각의 엔티티에 저장
        // 1. StoryCharacter 저장
        StoryCharacter character = StoryCharacter.builder()
                .category(Category.fromLabel(aiResponse.getTheme()))
                .imageUrl(aiResponse.getImage_url())

                .build();
        characterRepository.save(character);
        //Character 이름 수정 -> Why? : java.lang.Character 와의 충돌 때문

        // 2. Point 저장
        Point point = Point.builder()
                .message(aiResponse.getPoint_message())
                .cost(aiResponse.getPoint())
                .userId(user.getId())
                .build();
        pointRepository.save(point);

        // 3. Mission 저장 + Character와 연관
        Mission mission = Mission.builder()
                .movie(aiResponse.getMovie())
                .mission(aiResponse.getMission())
                .missionStatus(MissionStatus.AVAILABLE)
                .effect(aiResponse.getEffect())
                .storyCharacter(character)  // 캐릭터 연결
                .movieRecord(movieRecord)
                .point(point)
                .build();
        missionRepository.save(mission);

        MyMission myMission = MyMission.builder()
                .user(user)
                .mission(mission)
                .build();
        myMissionRepository.save(myMission);

        CollectingCharacters collectingRecord = CollectingCharacters.builder()
                .user(user)
                .storyCharacter(character)
                .build();
        collectingCharactersRepository.save(collectingRecord);


        return AiTaskDto.ResponseFromAi.builder()
                .movie(aiResponse.getMovie())
                .mission(aiResponse.getMission())
                .effect(aiResponse.getEffect())
                .point_message(aiResponse.getPoint_message())
                .point(aiResponse.getPoint())
                .theme(aiResponse.getTheme())
                .image_url(aiResponse.getImage_url())
                .build();

    }
}
