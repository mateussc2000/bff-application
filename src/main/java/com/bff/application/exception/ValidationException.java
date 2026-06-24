package com.bff.application.exception;

import com.bff.application.enums.ErrorCodeEnum;

public class ValidationException extends AbstractException {

    public ValidationException(String message) {
        super(ErrorCodeEnum.ERROR_VALIDATION.getCode(), message);
    }

    public ValidationException(String message, Throwable exception) {
        super(ErrorCodeEnum.ERROR_VALIDATION.getCode(), message, exception);
    }

}
