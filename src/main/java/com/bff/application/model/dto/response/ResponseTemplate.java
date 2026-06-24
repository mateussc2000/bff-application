package com.bff.application.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTemplate<T> {

    private T data;
    private ErrorResponse error;
    private Boolean ok;

    public static <T> ResponseTemplate<T> success(T data) {
        return ResponseTemplate.<T>builder()
                .ok(true)
                .data(data)
                .build();
    }

    public static <T> ResponseTemplate<T> failure(ErrorResponse error) {
        return ResponseTemplate.<T>builder()
                .ok(false)
                .error(error)
                .build();
    }

}
