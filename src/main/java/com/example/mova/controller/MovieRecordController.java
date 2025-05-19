package com.example.mova.controller;

import com.example.mova.config.JWTUtil;
import com.example.mova.dto.MovieRecordDto;
import com.example.mova.service.MovieRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class MovieRecordController {

    private final MovieRecordService movieRecordService;

    //홈화면의 영화 리스트 보여줌
    @GetMapping
    public ResponseEntity<List<MovieRecordDto.MovieListResponseDto>> findMovieList(){
        List<MovieRecordDto.MovieListResponseDto> list = movieRecordService.findMovieList();
        return ResponseEntity.ok(list);
    }

    //영화홈화면에 최신기록 보여주는 페이지
    @GetMapping("/latest")
    public ResponseEntity<List<MovieRecordDto.MovieLatestResponseDto>> findMovieLatest(){
        List<MovieRecordDto.MovieLatestResponseDto> latestList = movieRecordService.findLatest();
        return ResponseEntity.ok(latestList);
    }

    //영화기록 작성 페이지
    @PostMapping("/movies")
    public ResponseEntity<MovieRecordDto.MoiveRecordResponseDto> addMovieRecord(@Valid @RequestBody MovieRecordDto.MovieRecordRequestDto request){

        //현재 로그인한 사용자 이메일 꺼내기
        String email = JWTUtil.getCurrentUsername();

        MovieRecordDto.MoiveRecordResponseDto created = movieRecordService.addMovieRecord(request, email);
        //201 created 응답
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    //영화기록한 내용 조회페이지
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<MovieRecordDto.MoiveRecordResponseDto> findMovieRecord(@PathVariable Long movieId) {
        MovieRecordDto.MoiveRecordResponseDto movieResponses = movieRecordService.findMovieRecord(movieId);
        return ResponseEntity.ok().body(movieResponses);
    }
}
