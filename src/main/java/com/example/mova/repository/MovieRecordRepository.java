package com.example.mova.repository;

import com.example.mova.domain.MovieRecord;
import com.example.mova.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRecordRepository extends JpaRepository<MovieRecord, Long> {
    // 유저 + 제목으로 조회
    Optional<MovieRecord> findByUserAndTitle(User user, String title);

    // 사용자, 제목 상관없이 최신 두 건 조회
    List<MovieRecord> findTop2ByOrderByCreatedAtDesc();

}
