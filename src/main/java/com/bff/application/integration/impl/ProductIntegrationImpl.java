package com.bff.application.integration.impl;

import com.bff.application.client.ProductFeignClient;
import com.bff.application.exception.IntegrationException;
import com.bff.application.integration.ProductIntegration;
import com.bff.application.model.dto.integration.IntegrationProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductIntegrationImpl implements ProductIntegration {

    private final ProductFeignClient productFeignClient;

    @Override
    public List<IntegrationProductResponse> fetchProducts(String name) {
        log.info("ProductIntegrationImpl#fetchProducts: calling System 1, name={}", name);
        try {
            List<IntegrationProductResponse> response = productFeignClient.getProducts(name);
            log.info("ProductIntegrationImpl#fetchProducts: received {} products from System 1", response.size());
            return response;
        } catch (Exception e) {
            log.error("ProductIntegrationImpl#fetchProducts: error calling System 1 - {}", e.getMessage(), e);
            throw new IntegrationException("Error obtaining data from System 1: " + e.getMessage(), e);
        }
    }

    @Override
    public IntegrationProductResponse fetchProductById(Long id) {
        log.info("ProductIntegrationImpl#fetchProductById: calling System 1, id={}", id);
        try {
            IntegrationProductResponse response = productFeignClient.getProductById(id);
            log.info("ProductIntegrationImpl#fetchProductById: received product id={} from System 1", id);
            return response;
        } catch (Exception e) {
            log.error("ProductIntegrationImpl#fetchProductById: error calling System 1 - {}", e.getMessage(), e);
            throw new IntegrationException("Error obtaining data from System 1: " + e.getMessage(), e);
        }
    }

}
