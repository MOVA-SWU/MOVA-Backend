package com.example.mova.repository;

import com.example.mova.domain.MyMission;
import com.example.mova.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MyMissionRepository extends JpaRepository<MyMission, Long> {
    Optional<MyMission> findByMyMissionIdAndUser_IdAndMission_MovieRecord_Id(
            Long myMissionId, Long userId, Long movieRecordId
    );

    List<MyMission> findAllByUser_IdAndMissionStatus(Long userId, MissionStatus status);

    Optional<MyMission> findByUser_IdAndMission_MissionId(Long userId, Long missionId);

}
