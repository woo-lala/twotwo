package com.sparta.twotwo.common.response;

import com.sparta.twotwo.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private int status;
    private String message;

    private ErrorResponse(ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatusCode();
    }



}
