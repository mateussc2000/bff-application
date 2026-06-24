package com.bff.application.exception;

import com.bff.application.enums.ErrorCodeEnum;

public class ConverterException extends AbstractException {

    public ConverterException(String message) {
        super(ErrorCodeEnum.ERROR_CONVERSION.getCode(), message);
    }

    public ConverterException(String message, Throwable exception) {
        super(ErrorCodeEnum.ERROR_CONVERSION.getCode(), message, exception);
    }

}
