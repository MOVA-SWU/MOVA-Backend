package com.example.mova.controller;

import com.example.mova.config.JWTUtil;
import com.example.mova.domain.User;
import com.example.mova.dto.AiTaskDto;
import com.example.mova.dto.MovieRecordDto;
import com.example.mova.repository.UserRepository;
import com.example.mova.service.AiAnalyzeService;
import com.example.mova.service.MovieRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class MovieRecordController {

    private final MovieRecordService movieRecordService;
    private final AiAnalyzeService aiAnalyzeService;
    private final UserRepository userRepository;

    // 홈화면의 영화 리스트
    @GetMapping
    public ResponseEntity<List<MovieRecordDto.MovieListResponseDto>> findMovieList() {
        Long userId = getCurrentUserId();
        List<MovieRecordDto.MovieListResponseDto> list =
                movieRecordService.findMovieList(userId);
        return ResponseEntity.ok(list);
    }

    // 영화홈화면에 최신기록 보여주는 페이지
    @GetMapping("/latest")
    public ResponseEntity<List<MovieRecordDto.MovieLatestResponseDto>> findMovieLatest() {
        Long userId = getCurrentUserId();
        List<MovieRecordDto.MovieLatestResponseDto> latestList =
                movieRecordService.findLatest(userId);
        return ResponseEntity.ok(latestList);
    }

    // 영화 기록 작성
    @PostMapping("/movies")
    public ResponseEntity<AiTaskDto.ResponseFromAi> addMovieRecord(
            @Valid @RequestBody MovieRecordDto.MovieRecordRequestDto request) {

        Long userId = getCurrentUserId();
        AiTaskDto.ResponseFromAi response =
                aiAnalyzeService.analyzeAndSaveMovieRecord(request, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/movie-records/{movieRecordId}")
    public ResponseEntity<MovieRecordDto.MoiveRecordResponseDto> findMovieRecord(
            @PathVariable Long movieRecordId) {

        Long userId = getCurrentUserId();
        MovieRecordDto.MoiveRecordResponseDto dto =
                movieRecordService.findMovieRecord(movieRecordId, userId);

        return ResponseEntity.ok(dto);
    }

    // —————————————
    // JWTUtil로 email 추출 → UserRepository로 User 조회 → ID 반환
    private Long getCurrentUserId() {
        String email = JWTUtil.getCurrentUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("사용자를 찾을 수 없습니다. email=" + email)
                );
        return user.getId();
    }
}
