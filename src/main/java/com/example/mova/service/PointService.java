package com.example.mova.service;

import com.example.mova.dto.PointSumDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public PointSumDto getPointSum(long userId) {
        int totalCompletedPoints = pointRepository.sumCostByUserIdAndStatus(
                userId,
                MissionStatus.COMPLETED   // 완료 상태
        );
        return new PointSumDto(totalCompletedPoints);
    }
}
