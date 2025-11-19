package com.example.mova.enums;

import com.example.mova.errorhandler.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"400 Bad Request", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403 Forbidden", "요청한 콘텐츠에 접근할 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404 Not Found", "요청한 URI를 찾을 수 없습니다."),
    CONFLICT(HttpStatus.CONFLICT, "409 Resources That Exist", "이미 존재하는 리소스입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500 Internal Server Error", "코드 내부의 문제입니다."),
    FILE_TOO_LARGE(HttpStatus.BAD_REQUEST, "IMAGE_UPLOAD4001", "첨부하는 이미지의 크기가 너무 큽니다."),
    NO_IMAGE_EXIST(HttpStatus.NOT_FOUND, "IMAGE_UPLOAD4002", "해당 이미지를 찾을 수 없습니다."),
    FAIL_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "IMAGE_UPLOAD5001", "S3에 이미지 업로드가 실패했습니다."),
    FAIL_DELETE(HttpStatus.INTERNAL_SERVER_ERROR, "IMAGE_UPLOAD5002", "이미지 삭제를 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private String message;

}
