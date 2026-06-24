package com.bff.application.client;

import com.bff.application.client.config.FeignClientConfig;
import com.bff.application.model.dto.integration.IntegrationProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "product-service",
        url = "${integration.product-service.base-url}",
        configuration = FeignClientConfig.class
)
public interface ProductFeignClient {

    @GetMapping("/mock/api/products")
    List<IntegrationProductResponse> getProducts(
            @RequestParam(required = false) String name);

    @GetMapping("/mock/api/products/{id}")
    IntegrationProductResponse getProductById(@PathVariable("id") Long id);

}
