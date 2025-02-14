package com.sparta.twotwo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private ErrorCode errorCode;
    private String message;

    public static ErrorResponse from(ErrorCode errorCode){
        return new ErrorResponse(errorCode, errorCode.getMessage());
    }

}
