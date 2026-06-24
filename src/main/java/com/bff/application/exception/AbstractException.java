package com.bff.application.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {

    private final String logCode;

    protected AbstractException(String logCode, String message) {
        super(message);
        this.logCode = logCode;
    }

    protected AbstractException(String logCode, String message, Throwable exception) {
        super(message, exception);
        this.logCode = logCode;
    }

}
