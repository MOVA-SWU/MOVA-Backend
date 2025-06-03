package com.example.mova.repository;

import com.example.mova.domain.CollectingCharacters;
import com.example.mova.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollectingCharactersRepository extends JpaRepository<CollectingCharacters, Long> {
    List<CollectingCharacters> findByUserId(Long userId);

    @Query("""
        SELECT DISTINCT sc.imageUrl
          FROM CollectingCharacters cc
          JOIN cc.storyCharacter sc
          JOIN sc.missionList m
         WHERE cc.user.id = :userId
           AND m.movieRecord.user.id = :userId
           AND m.missionStatus = :status
        """)
    List<String> findImageUrlsByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") MissionStatus status
    );
}
