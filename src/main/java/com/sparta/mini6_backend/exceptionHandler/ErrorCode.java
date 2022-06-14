package com.sparta.mini6_backend.exceptionHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 400 Bad Request
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, 409, "중복된 사용자명이 존재합니다."),
    BELOW_MIN_MY_PRICE(HttpStatus.BAD_REQUEST, 400, "최저 희망가는 최소 원 이상으로 설정해 주세요."),

    // 404 Not Found
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, 404, "해당 관심상품 아이디가 존재하지 않습니다."),
    NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, 404, "해당 폴더 아이디가 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, int errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}