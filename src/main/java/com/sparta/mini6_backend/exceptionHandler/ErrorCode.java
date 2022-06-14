package com.sparta.mini6_backend.exceptionHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 400 Bad Request
    EMPTY_CONTENT(HttpStatus.BAD_REQUEST,"필수입력값이 없습니다."),
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "만료되었거나 유효하지 않은 토큰입니다."),
    /* 403 FORBIDDEN : 권한이 없는 사용자 */
    INVALID_AUTHORITY(HttpStatus.FORBIDDEN,"권한이 없는 사용자 입니다"),
    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 코멘트를 찾을 수 없습니다"),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 좋아요를 찾을 수 없습니다"),
    PIN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 핀을 찾을 수 없습니다"),
    AUTH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다"),
    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 사용자명이 존재합니다")
    ;

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}