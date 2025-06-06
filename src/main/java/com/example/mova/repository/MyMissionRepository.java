package com.example.mova.repository;

import com.example.mova.domain.MyMission;
import com.example.mova.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MyMissionRepository extends JpaRepository<MyMission, Long> {

    Optional<MyMission> findByMission_MissionId(Long missionId);

    List<MyMission> findAllByMission_MissionStatus(MissionStatus status);



}
