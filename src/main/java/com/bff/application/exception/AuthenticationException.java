package com.bff.application.exception;

import com.bff.application.enums.ErrorCodeEnum;

public class AuthenticationException extends AbstractException {

    public AuthenticationException(String message) {
        super(ErrorCodeEnum.ERROR_TOKEN.getCode(), message);
    }

    public AuthenticationException(String message, Throwable exception) {
        super(ErrorCodeEnum.ERROR_TOKEN.getCode(), message, exception);
    }

}
