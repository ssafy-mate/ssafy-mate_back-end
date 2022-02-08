package com.ssafy.ssafymate.exception;

import javax.servlet.ServletException;

public class JwtException extends ServletException {

    private ErrorCode errorCode;

    public JwtException() {
        super(ErrorCode.JWT_ERROR.getMessage());
        this.errorCode = ErrorCode.JWT_ERROR;
    }
}
