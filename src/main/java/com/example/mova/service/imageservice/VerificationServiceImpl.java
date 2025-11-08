package com.example.mova.service.imageservice;

import com.amazonaws.services.s3.AmazonS3;
import com.example.mova.config.GoogleCloudStorageConfig;
import com.example.mova.dto.VerificationDto;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService{

    private final Storage storage;

    @Value("${GCP_STORAGE_BUCKET_NAME}")
    private String bucketName;

    @Override
    public VerificationDto.checkResponseDto upload(MultipartFile file) {
        try {
            // 업로드할 파일 이름 생성
            String uuid = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            String objectName = "verification/" + uuid +"-" + originalFilename;

            // Blob 정보 생성
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName)
                    .setContentType(file.getContentType())
                    .build();

            // 실제 GCS 업로드 수행
            storage.create(blobInfo, file.getInputStream());

            // 업로드된 파일 URL 생성
            String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);

            // 응답 DTO 반환
            return VerificationDto.checkResponseDto.builder()
                    .checkedUrl(fileUrl)
                    .key(objectName)
                    .build();
        }
        catch (IOException e){
            log.error("파일 업로드 실해: {}", e.getMessage());
            throw new RuntimeException("GCS 업로드 실패", e);
        }
    }

    @Override
    public void delete(String key){
        boolean deleted = storage.delete(bucketName, key);
        if (!deleted){
            throw new RuntimeException("파일 삭제 실패 또는 존재하지 않음:" + key);
        }
    }

}
