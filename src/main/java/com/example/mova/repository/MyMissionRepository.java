package com.example.mova.repository;

import com.example.mova.domain.MyMission;
import com.example.mova.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyMissionRepository extends JpaRepository<MyMission, Long> {
    List<MyMission> findAllByMissionStatus(MissionStatus missionStatus);
}
