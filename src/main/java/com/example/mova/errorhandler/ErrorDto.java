package com.example.mova.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ErrorDto {
    @Getter
    @AllArgsConstructor
    public static class ErrorResponse{
        private String code;
        private String message;
    }
}
