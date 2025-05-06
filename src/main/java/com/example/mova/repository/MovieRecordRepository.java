package com.example.mova.repository;

import com.example.mova.domain.MovieRecord;
import com.example.mova.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRecordRepository extends JpaRepository<MovieRecord, Long> {
    // 유저 + 제목으로 조회
    Optional<MovieRecord> findByUserAndTitle(User user, String title);
}
