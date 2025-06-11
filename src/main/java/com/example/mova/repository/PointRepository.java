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
          FROM MyMission mm
          JOIN mm.mission m
          JOIN m.point p
         WHERE mm.user.id = :userId
           AND mm.missionStatus = :status
        """)
    int sumCostByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status")   MissionStatus status
    );

}
