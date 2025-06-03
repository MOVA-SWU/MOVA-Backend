package com.example.mova.repository;

import com.example.mova.domain.Point;
import com.example.mova.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Query("SELECT COALESCE(SUM(p.cost), 0) FROM Point p WHERE p.userId = :userId")
    int sumCostByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT COALESCE(SUM(p.cost), 0)
          FROM Mission m
          JOIN m.point p
         WHERE p.userId = :userId
           AND m.missionStatus = :status
        """)
    int sumCostByUserIdAndMissionStatus(
            @Param("userId") Long userId,
            @Param("status") MissionStatus status
    );

}
