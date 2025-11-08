package com.example.mova.controller;

import com.example.mova.dto.VerificationDto;
import com.example.mova.service.imageservice.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verification")
public class VerificationController {
    private final VerificationService verify;

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
}
