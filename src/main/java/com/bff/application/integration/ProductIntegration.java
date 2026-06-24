package com.bff.application.integration;

import com.bff.application.model.dto.integration.IntegrationProductResponse;

import java.util.List;

public interface ProductIntegration {

    List<IntegrationProductResponse> fetchProducts(String name);

    IntegrationProductResponse fetchProductById(Long id);

}
