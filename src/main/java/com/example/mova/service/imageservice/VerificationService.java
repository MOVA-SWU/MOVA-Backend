package com.example.mova.service.imageservice;

import com.example.mova.dto.VerificationDto;
import org.springframework.web.multipart.MultipartFile;

public interface VerificationService {

    VerificationDto.checkResponseDto upload(MultipartFile file);

    void delete(String key);
}
