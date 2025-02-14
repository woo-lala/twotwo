package com.sparta.twotwo.common.response;

import com.sparta.twotwo.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private ErrorCode errorCode;
    private String message;

    private ErrorResponse(ErrorCode errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse from(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }



}
