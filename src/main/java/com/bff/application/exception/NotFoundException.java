package com.bff.application.exception;

import com.bff.application.enums.ErrorCodeEnum;

public class NotFoundException extends AbstractException {

    public NotFoundException(String message) {
        super(ErrorCodeEnum.ERROR_NOT_FOUND.getCode(), message);
    }

    public NotFoundException(String message, Throwable exception) {
        super(ErrorCodeEnum.ERROR_NOT_FOUND.getCode(), message, exception);
    }

}
