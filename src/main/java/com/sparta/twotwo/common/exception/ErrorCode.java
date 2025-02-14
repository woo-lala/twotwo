package com.sparta.twotwo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    EXAMPLE(HttpStatus.FORBIDDEN,"예시");

    private final int statusCode;
    private final String message;


    ErrorCode(HttpStatus statusCode, String message) {
        this.statusCode = statusCode.value();
        this.message = message;
    }


}
