package com.sparta.twotwo.common.response;

import com.sparta.twotwo.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private ErrorCode errorCode;

    private ErrorResponse(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }



}
