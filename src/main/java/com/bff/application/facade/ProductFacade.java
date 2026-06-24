package com.bff.application.facade;

import com.bff.application.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductFacade {

    public void validateListParams(String name) {
        log.debug("ProductFacade#validateListParams: name={}", name);
        if (name != null && name.length() > 100) {
            throw new ValidationException("'name' filter must not exceed 100 characters");
        }
    }

    public void validatePaginatedParams(int page, int size, String name) {
        log.debug("ProductFacade#validatePaginatedParams: page={}, size={}, name={}", page, size, name);
        if (page < 0) {
            throw new ValidationException("'page' must be >= 0, received: " + page);
        }
        if (size <= 0 || size > 100) {
            throw new ValidationException("'size' must be between 1 and 100, received: " + size);
        }
        validateListParams(name);
    }

    public void validateDetailParams(Long id) {
        log.debug("ProductFacade#validateDetailParams: id={}", id);
        if (id == null || id <= 0) {
            throw new ValidationException("'id' must be a positive number, received: " + id);
        }
    }

}
