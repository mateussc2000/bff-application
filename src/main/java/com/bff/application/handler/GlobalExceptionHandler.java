package com.bff.application.handler;

import com.bff.application.enums.ErrorCodeEnum;
import com.bff.application.exception.*;
import com.bff.application.model.dto.response.ErrorResponse;
import com.bff.application.model.dto.response.ResponseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseTemplate<Void>> handleNotFound(NotFoundException ex) {
        log.warn("NotFoundException [{}]: {}", ex.getLogCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(ex.getLogCode(), ErrorCodeEnum.ERROR_NOT_FOUND.getDescription(), ex.getMessage())));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseTemplate<Void>> handleValidation(ValidationException ex) {
        log.warn("ValidationException [{}]: {}", ex.getLogCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(ex.getLogCode(), ErrorCodeEnum.ERROR_VALIDATION.getDescription(), ex.getMessage())));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseTemplate<Void>> handleAuthentication(AuthenticationException ex) {
        log.warn("AuthenticationException [{}]: {}", ex.getLogCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(ex.getLogCode(), ErrorCodeEnum.ERROR_TOKEN.getDescription(), ex.getMessage())));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseTemplate<Void>> handleBusiness(BusinessException ex) {
        log.warn("BusinessException [{}]: {}", ex.getLogCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(ex.getLogCode(), ErrorCodeEnum.ERROR_BUSINESS.getDescription(), ex.getMessage())));
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ResponseTemplate<Void>> handleIntegration(IntegrationException ex) {
        log.error("IntegrationException [{}]: {}", ex.getLogCode(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(ex.getLogCode(), ErrorCodeEnum.ERROR_INTEGRATION.getDescription(), ex.getMessage())));
    }

    @ExceptionHandler(MapperException.class)
    public ResponseEntity<ResponseTemplate<Void>> handleMapper(MapperException ex) {
        log.error("MapperException [{}]: {}", ex.getLogCode(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(ex.getLogCode(), ErrorCodeEnum.ERROR_MAPPING.getDescription(), ex.getMessage())));
    }

    @ExceptionHandler(ConverterException.class)
    public ResponseEntity<ResponseTemplate<Void>> handleConverter(ConverterException ex) {
        log.error("ConverterException [{}]: {}", ex.getLogCode(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(ex.getLogCode(), ErrorCodeEnum.ERROR_CONVERSION.getDescription(), ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseTemplate<Void>> handleBeanValidation(MethodArgumentNotValidException ex) {
        String details = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fe) {
                        return fe.getField() + ": " + fe.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.joining("; "));
        log.warn("MethodArgumentNotValidException: {}", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(ErrorCodeEnum.ERROR_VALIDATION.getCode(),
                                ErrorCodeEnum.ERROR_VALIDATION.getDescription(), details)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseTemplate<Void>> handleGeneric(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseTemplate.failure(
                        ErrorResponse.of(ErrorCodeEnum.ERROR_SYSTEM.getCode(),
                                ErrorCodeEnum.ERROR_SYSTEM.getDescription(), "An unexpected error occurred")));
    }

}
