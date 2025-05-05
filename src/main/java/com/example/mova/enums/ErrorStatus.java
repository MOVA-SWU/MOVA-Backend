package com.example.mova.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorStatus {
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"400 Bad Request", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403 Forbidden", "요청한 콘텐츠에 접근할 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404 Not Found", "요청한 URI를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500 Internal Server Error")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private String message;

}
