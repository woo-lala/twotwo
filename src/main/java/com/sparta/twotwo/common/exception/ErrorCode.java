package com.sparta.twotwo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //4XX
    STORE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "가게 요청의 구문이 잘못되었습니다."),
    PRODUCT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "메뉴 요청의 구문이 잘못되었습니다."),
    ORDER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "메뉴 요청의 구문이 잘못되었습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청의 구문이 잘못되었습니다."),
    NO_ADDRESS_CHANGES(HttpStatus.BAD_REQUEST, "변경된 주소 정보가 없습니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "지정한 리소스에 대한 액세스 권한이 없습니다."),
    PAYMENT_REQUIRED(HttpStatus.PAYMENT_REQUIRED, "지정한 리소스를 액세스하기 위해서는 결제가 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "지정한 리소스에 대한 액세스가 금지되었습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "지정한 리소스를 찾을 수 없습니다."),

    MEMBER_EMAIL_EXIST(HttpStatus.CONFLICT, "중복된 이메일 입니다."),
    MEMBER_USERNAME_EXIST(HttpStatus.CONFLICT, "중복된 아이디 입니다."),
    STORE_NAME_EXIST(HttpStatus.CONFLICT, "중복된 가게명 입니다."),
    ADDRESS_EXIST(HttpStatus.CONFLICT, "중복된 주소 입니다."),

    CONFLICT(HttpStatus.CONFLICT, "서버가 요청을 수행하는 중에 충돌이 발생하였습니다."),

    TIMEOUT_UPDATE_ORDER(HttpStatus.REQUEST_TIMEOUT, "주문 수정 가능 시간이 초과했습니다."),

    //5XX
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 에러가 발생하였습니다."),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, "요청한 URI의 메소드에 대해 서버가 구현하고 있지 않습니다."),
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "게이트웨이 또는 프록시 역할을 하는 서버가 그 뒷단의 서버로부터 잘못된 응답을 받았습니다."),

    ;

    private HttpStatus status;
    private String message;
}
