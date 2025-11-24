package com.example.mova.service.AiVerification;

import com.example.mova.dto.AiTaskDto;

public interface AiVerificationService {

    AiTaskDto.receiveFromAi verifyImage(Long myMissionId, Long userId, AiTaskDto.sendImageFromAi request);
}
