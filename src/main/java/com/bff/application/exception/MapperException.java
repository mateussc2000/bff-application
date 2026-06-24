package com.bff.application.exception;

import com.bff.application.enums.ErrorCodeEnum;

public class MapperException extends AbstractException {

    public MapperException(String message) {
        super(ErrorCodeEnum.ERROR_MAPPING.getCode(), message);
    }

    public MapperException(String message, Throwable exception) {
        super(ErrorCodeEnum.ERROR_MAPPING.getCode(), message, exception);
    }

}
