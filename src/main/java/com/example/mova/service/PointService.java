package com.example.mova.service;

import com.example.mova.dto.PointSumDto;
import com.example.mova.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public PointSumDto getPointSum(long userId){
        int total = pointRepository.sumCostByUserId(userId);
        return new PointSumDto(total);
    }
}
