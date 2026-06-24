package com.bff.application.utils;

import com.bff.application.exception.ValidationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validation {

    public String validateNameFilter(String name) {
        if (name != null && name.length() > 100) {
            throw new ValidationException("'name' filter must not exceed 100 characters");
        }
        return name;
    }

    public int validatePage(int page) {
        if (page < 0) {
            throw new ValidationException("'page' must be >= 0, received: " + page);
        }
        return page;
    }

    public int validateSize(int size) {
        if (size <= 0 || size > 100) {
            throw new ValidationException("'size' must be between 1 and 100, received: " + size);
        }
        return size;
    }

    public Long validateId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("'id' must be a positive number, received: " + id);
        }
        return id;
    }
}
