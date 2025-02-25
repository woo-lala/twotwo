package com.sparta.twotwo.common.exception;

import com.sparta.twotwo.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(TwotwoApplicationException.class)
    public ResponseEntity<ErrorResponse> handleCustomExceptions(TwotwoApplicationException e) {
        logger.error("ERROR ::: [RuntimeException] ", e);
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ErrorResponse.from(e.getErrorCode(), e.getErrorCode().getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.BAD_REQUEST, String.join(", ", errors));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ErrorResponse handleAllExceptions(Exception e) {
        logger.error("ERROR ::: [AllException] ", e);
        return ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
    }
}


