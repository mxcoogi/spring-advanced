package org.example.expert.config.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    JWT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "JWT 토큰이 필요합니다."),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 서명입니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    JWT_UNSUPPORTED(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰입니다."),
    JWT_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않는 JWT 토큰입니다."),
    JWT_FORBIDDEN(HttpStatus.FORBIDDEN, "관리자 권한이 없습니다.");
    private HttpStatus httpStatus;
    private String message;

    ErrorType(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
