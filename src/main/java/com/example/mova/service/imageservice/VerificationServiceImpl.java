package com.example.mova.service.imageservice;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.mova.dto.VerificationDto;
import com.example.mova.enums.ErrorStatus;
import com.example.mova.errorhandler.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService{

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSizeString;

    @Override
    public VerificationDto.checkResponseDto upload(MultipartFile file){

        // 용량 체크
        long maxBytes = DataSize.parse(maxSizeString).toBytes();
        if (file.getSize() > maxBytes){
            throw new GeneralException(ErrorStatus.FILE_TOO_LARGE);
        }

        String key = generateRandomFilename(file);

        // 메타데이터 설정
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.getSize());
        meta.setContentType(file.getContentType());

        // S3 업로드
        try (InputStream in = file.getInputStream()) {
            amazonS3.putObject(bucket, key, in, meta);
        } catch (IOException e) {
            log.error("IO error: {}", e.getMessage());
            throw new GeneralException(ErrorStatus.FAIL_UPLOAD);
        } catch (AmazonS3Exception e) {
            log.error("S3 service error: {}", e.getMessage());
            throw new GeneralException(ErrorStatus.FAIL_UPLOAD);
        } catch (SdkClientException e) {
            log.error("AWS SDK client error: {}", e.getMessage());
            throw new GeneralException(ErrorStatus.FAIL_UPLOAD);
        }

        // 결과 반환
        String checkedUrl = amazonS3.getUrl(bucket, key).toString();
        return new VerificationDto.checkResponseDto(checkedUrl, key);

    }

    @Override
    public void delete(String key){
        if (!amazonS3.doesObjectExist(bucket, key)){
            throw  new GeneralException(ErrorStatus.NO_IMAGE_EXIST);
        }
        try {
            amazonS3.deleteObject(bucket, key);
        } catch (AmazonS3Exception e) {
            log.error("S3 service delete error: {}", e.getMessage());
            throw new GeneralException(ErrorStatus.FAIL_DELETE);
        } catch (SdkClientException e) {
            log.error("AWS SDK client error on delete: {}", e.getMessage());
            throw new GeneralException(ErrorStatus.FAIL_DELETE);
        }
        log.info("Deleted S3 object: {}", key);
    }

    private String generateRandomFilename(MultipartFile multipartFile) {
        String original = multipartFile.getOriginalFilename();
        String ext = validateFileExtension(Objects.requireNonNull(original));
        return UUID.randomUUID() + "." + ext;
    }

    private String validateFileExtension(String filename) {
        String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        List<String> allowed = List.of("jpg", "jpeg", "png", "gif");
        if (!allowed.contains(ext)) {
            throw new GeneralException(ErrorStatus.NO_IMAGE_EXIST);
        }
        return ext;
    }

    @Override
    public String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
