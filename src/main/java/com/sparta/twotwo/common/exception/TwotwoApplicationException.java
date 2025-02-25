package com.sparta.twotwo.common.exception;

import lombok.Getter;

@Getter
public class TwotwoApplicationException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String message;

    public TwotwoApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

}
