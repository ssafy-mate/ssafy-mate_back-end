package com.ssafy.ssafymate.exception;

public class EmailCodeException extends InvalidValueException {
    public EmailCodeException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
