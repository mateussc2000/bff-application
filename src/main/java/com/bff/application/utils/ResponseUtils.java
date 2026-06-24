package com.bff.application.utils;

import com.bff.application.enums.ErrorCodeEnum;
import com.bff.application.model.dto.response.ErrorResponse;
import com.bff.application.model.dto.response.ResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtils {

    private ResponseUtils() {}

    public static <T> ResponseEntity<ResponseTemplate<T>> ok(T data) {
        return ResponseEntity.ok(ResponseTemplate.success(data));
    }

    public static <T> ResponseEntity<ResponseTemplate<T>> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseTemplate.success(data));
    }

    public static <T> ResponseEntity<ResponseTemplate<T>> notFound(ErrorCodeEnum errorCode, String message) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(errorCode.getCode(), errorCode.getDescription(), message)));
    }

    public static <T> ResponseEntity<ResponseTemplate<T>> badRequest(ErrorCodeEnum errorCode, String message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(errorCode.getCode(), errorCode.getDescription(), message)));
    }

    public static <T> ResponseEntity<ResponseTemplate<T>> internalError(ErrorCodeEnum errorCode, String message) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(errorCode.getCode(), errorCode.getDescription(), message)));
    }

    public static <T> ResponseEntity<ResponseTemplate<T>> unauthorized(ErrorCodeEnum errorCode, String message) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(errorCode.getCode(), errorCode.getDescription(), message)));
    }

}
