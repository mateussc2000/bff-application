package com.bff.application.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String description;
    private String message;
    private LocalDateTime timestamp;

    public static ErrorResponse of(String code, String description, String message) {
        return ErrorResponse.builder()
                .code(code)
                .description(description)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
