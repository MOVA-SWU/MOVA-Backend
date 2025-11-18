package com.example.mova.controller;

import com.example.mova.config.JWTUtil;
import com.example.mova.domain.User;
import com.example.mova.dto.AiTaskDto;
import com.example.mova.dto.VerificationDto;
import com.example.mova.repository.UserRepository;
import com.example.mova.service.AiVerification.AiVerificationService;
import com.example.mova.service.imageservice.VerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verifications")
public class VerificationController {
    private final VerificationService verify;
    private final AiVerificationService aiVerify;
    private final UserRepository userRepository;

    @PostMapping(value = "/images/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VerificationDto.checkResponseDto> uploadImage( @RequestPart("file")MultipartFile file){

        VerificationDto.checkResponseDto result = verify.upload(file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @DeleteMapping("/images/{key}")
    public ResponseEntity<String> deleteImage(@PathVariable String key){
        verify.delete(key);
        return ResponseEntity.ok("이미지가 성공적으로 삭제되었습니다.");
    }

    // 미션-AI 검증 API
    @PostMapping("/movie-records/{movieRecordId}")
    public ResponseEntity<String> verifiedImage(
            @PathVariable Long movieRecordId,
            @Valid @RequestBody AiTaskDto.sendImageFromAi request) {

        Long userId = getCurrentUserId();
        String response = aiVerify.verifyImage(movieRecordId, userId, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    private Long getCurrentUserId(){
        String email = JWTUtil.getCurrentUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. email = " + email));

        return user.getId();
    }
}
