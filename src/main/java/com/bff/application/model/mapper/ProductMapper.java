package com.bff.application.model.mapper;

import com.bff.application.enums.ProductStatusEnum;
import com.bff.application.exception.MapperException;
import com.bff.application.model.dto.integration.IntegrationProductResponse;
import com.bff.application.model.dto.response.ProductDetailResponse;
import com.bff.application.model.dto.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductResponse toResponse(IntegrationProductResponse source) {
        try {
            return ProductResponse.builder()
                    .id(source.id())
                    .name(source.name())
                    .price(source.price())
                    .status(parseStatus(source.status()))
                    .build();
        } catch (Exception e) {
            throw new MapperException("Failed to map IntegrationProductResponse to ProductResponse", e);
        }
    }

    public ProductDetailResponse toDetailResponse(IntegrationProductResponse source) {
        try {
            return ProductDetailResponse.builder()
                    .id(source.id())
                    .name(source.name())
                    .description(source.description())
                    .price(source.price())
                    .status(parseStatus(source.status()))
                    .createdAt(source.createdAt())
                    .updatedAt(source.updatedAt())
                    .build();
        } catch (Exception e) {
            throw new MapperException("Failed to map IntegrationProductResponse to ProductDetailResponse", e);
        }
    }

    public List<ProductResponse> toResponseList(List<IntegrationProductResponse> sources) {
        return sources.stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductStatusEnum parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return ProductStatusEnum.DONE;
        }
        try {
            return ProductStatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ProductStatusEnum.DONE;
        }
    }

}
