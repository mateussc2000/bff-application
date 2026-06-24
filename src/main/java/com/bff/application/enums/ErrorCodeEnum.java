package com.bff.application.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodeEnum {

    ERROR_MAPPING("ERR-001", "Error during mapping operation"),
    ERROR_NOT_FOUND("ERR-002", "Resource not found"),
    ERROR_INTEGRATION("ERR-003", "Integration error occurred"),
    ERROR_TOKEN("ERR-004", "Token authentication error"),
    ERROR_SYSTEM("ERR-005", "Internal system error"),
    ERROR_OBTAINING_DATA_SYSTEM1("ERR-006", "Error obtaining data from System 1"),
    ERROR_OBTAINING_DATA_SYSTEM2("ERR-007", "Error obtaining data from System 2"),
    ERROR_CONVERSION("ERR-008", "Error during data conversion"),
    ERROR_VALIDATION("ERR-009", "Validation error"),
    ERROR_BUSINESS("ERR-010", "Business rule violation");

    private final String code;
    private final String description;

    @Override
    public String toString() {
        return code + " - " + description;
    }

}
