package com.bff.application.facade;

import com.bff.application.utils.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductFacade {

    public String validateListParams(String name) {
        log.debug("ProductFacade#validateListParams: name={}", name);
        return Validation.validateNameFilter(name);
    }

    public PaginatedParams validatePaginatedParams(int page, int size, String name) {
        log.debug("ProductFacade#validatePaginatedParams: page={}, size={}, name={}", page, size, name);
        return new PaginatedParams(
                Validation.validatePage(page),
                Validation.validateSize(size),
                validateListParams(name));
    }

    public Long validateDetailParams(Long id) {
        log.debug("ProductFacade#validateDetailParams: id={}", id);
        return Validation.validateId(id);
    }

    public record PaginatedParams(int page, int size, String name) {
    }

}
