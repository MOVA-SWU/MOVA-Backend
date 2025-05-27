package com.example.mova.repository;

import com.example.mova.domain.Mission;
import com.example.mova.domain.MyMission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Optional<Mission> findByMovieRecordId(Long movieRecordId);
    Optional<Mission> findByMissionIdAndMovieRecordId(Long movieRecordId, Long missionId);

}
