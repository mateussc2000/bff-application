package com.bff.application.exception;

import com.bff.application.enums.ErrorCodeEnum;

public class BusinessException extends AbstractException {

    public BusinessException(String message) {
        super(ErrorCodeEnum.ERROR_BUSINESS.getCode(), message);
    }

    public BusinessException(String message, Throwable exception) {
        super(ErrorCodeEnum.ERROR_BUSINESS.getCode(), message, exception);
    }

}
