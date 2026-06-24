package com.bff.application.exception;

import com.bff.application.enums.ErrorCodeEnum;

public class IntegrationException extends AbstractException {

    public IntegrationException(String message) {
        super(ErrorCodeEnum.ERROR_INTEGRATION.getCode(), message);
    }

    public IntegrationException(String message, Throwable exception) {
        super(ErrorCodeEnum.ERROR_INTEGRATION.getCode(), message, exception);
    }

}
