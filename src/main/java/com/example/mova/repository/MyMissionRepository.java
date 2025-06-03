package com.example.mova.repository;

import com.example.mova.domain.MyMission;
import com.example.mova.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyMissionRepository extends JpaRepository<MyMission, Long> {
    List<MyMission> findAllByMission_MissionStatus(MissionStatus missionStatus);

    //Optional<MyMission> findByMission_Id(Long missionId);
    @Query("""
        SELECT m
          FROM MyMission m
          JOIN m.mission ms
         WHERE ms.missionStatus = :status
        """)
    List<MyMission> findAllByMissionStatusJPQL(@Param("status") MissionStatus status);

}
