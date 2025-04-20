package org.example.expert.config.error;

import lombok.Getter;

@Getter
public class MyJwtException extends RuntimeException{
    private final ErrorType errorType;
    public MyJwtException(ErrorType errorType){
        this.errorType = errorType;
    }
}
