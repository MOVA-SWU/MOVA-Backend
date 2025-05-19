package com.example.mova.service;

import com.example.mova.domain.MovieRecord;
import com.example.mova.domain.User;
import com.example.mova.dto.MovieRecordDto;
import com.example.mova.errorhandler.DuplicateRecordException;
import com.example.mova.repository.MovieRecordRepository;
import com.example.mova.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieRecordService {

    private final MovieRecordRepository movieRecordRepository;
    private final UserRepository userRepository;

    //홈화면 목록 조회
    public List<MovieRecordDto.MovieListResponseDto> findMovieList(){
        //List<MovieRecord> movieList = movieRecordRepository.findAll();
        return movieRecordRepository.findAll().stream()
                .map(movie -> MovieRecordDto.MovieListResponseDto.builder()
                        .movieId(movie.getId())
                        .imageUrl(movie.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    //홈화면에 최신 기록 두 건 조회
    public List<MovieRecordDto.MovieLatestResponseDto> findLatest(){
        return movieRecordRepository.findTop2ByOrderByCreatedAtDesc().stream()
                .map(movieRecord -> MovieRecordDto.MovieLatestResponseDto.builder()
                        .movieId(movieRecord.getId())
                        .imageUrl(movieRecord.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }


    //기록 추가
    @Transactional
    public MovieRecordDto.MoiveRecordResponseDto addMovieRecord(MovieRecordDto.MovieRecordRequestDto request, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        movieRecordRepository
                .findByUserAndTitle(user, request.getTitle())
                .ifPresent(existing ->{
                    throw new DuplicateRecordException("이미 등록된 기록 입니다. id =" + existing.getId()
                    );
                });
        //DTO -> 엔티티
        MovieRecord movieRecord = MovieRecord.builder()
                .title(request.getTitle())
                .rating(request.getRating())
                .dateTime(request.getDateTime())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .user(user)
                .build();

        //DB에 실제로 저장하고, 저장된 엔티티를 받아옴
        MovieRecord saved = movieRecordRepository.save(movieRecord);

        return MovieRecordDto.MoiveRecordResponseDto.builder()
                .movieId(saved.getId())
                .title(saved.getTitle())
                .rating(saved.getRating())
                .content(saved.getContent())
                .dateTime(saved.getDateTime())
                .imageUrl(saved.getImageUrl())
                .build();
    }

    public MovieRecordDto.MoiveRecordResponseDto findMovieRecord(long movieId){
        MovieRecord movieRecord = movieRecordRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        return MovieRecordDto.MoiveRecordResponseDto.builder()
                .movieId(movieRecord.getId())
                .title(movieRecord.getTitle())
                .rating(movieRecord.getRating())
                .content(movieRecord.getContent())
                .dateTime(movieRecord.getDateTime())
                .imageUrl(movieRecord.getImageUrl())
                .build();
    }
}