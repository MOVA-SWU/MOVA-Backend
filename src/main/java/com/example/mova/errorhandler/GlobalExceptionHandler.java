package com.example.mova.errorhandler;


import com.example.mova.enums.ErrorStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto.ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorDto.ErrorResponse body = new ErrorDto.ErrorResponse(
                ErrorStatus.BAD_REQUEST.getCode(), msg);
        return ResponseEntity
                .status(ErrorStatus.BAD_REQUEST.getHttpStatus())
                .body(body);
    }

    // 2) 로그인 인증 실패
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto.ErrorResponse> handleAuthFail(BadCredentialsException ex) {
        ErrorDto.ErrorResponse body = new ErrorDto.ErrorResponse(
                ErrorStatus.UNAUTHORIZED.getCode(),
                "이메일 또는 비밀번호를 확인하세요."
        );
        return ResponseEntity
                .status(ErrorStatus.UNAUTHORIZED.getHttpStatus())
                .body(body);
    }

    // 3) 서비스 레벨 예외 (중복 / 없는 이메일)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto.ErrorResponse> handleIllegalArg(IllegalArgumentException ex) {
        ErrorStatus status = ErrorStatus.BAD_REQUEST;
        if ("없는 이메일입니다.".equals(ex.getMessage())) {
            status = ErrorStatus.NOT_FOUND;
        }
        ErrorDto.ErrorResponse body = new ErrorDto.ErrorResponse(status.getCode(), ex.getMessage());
        return ResponseEntity
                .status(status.getHttpStatus())
                .body(body);
    }
}
