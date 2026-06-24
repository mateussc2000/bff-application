package com.bff.application.service.impl;

import com.bff.application.integration.ProductIntegration;
import com.bff.application.model.dto.integration.IntegrationProductResponse;
import com.bff.application.model.dto.response.PaginatedResponse;
import com.bff.application.model.dto.response.ProductDetailResponse;
import com.bff.application.model.dto.response.ProductResponse;
import com.bff.application.model.mapper.ProductMapper;
import com.bff.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductIntegration productIntegration;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> listProducts(String name) {
        log.info("ProductServiceImpl#listProducts: fetching products, name={}", name);
        List<IntegrationProductResponse> integrationResponses = productIntegration.fetchProducts(name);
        List<ProductResponse> responses = productMapper.toResponseList(integrationResponses);
        log.info("ProductServiceImpl#listProducts: returning {} products", responses.size());
        return responses;
    }

    @Override
    public PaginatedResponse<ProductResponse> listProductsPaginated(int page, int size, String name) {
        log.info("ProductServiceImpl#listProductsPaginated: page={}, size={}, name={}", page, size, name);
        List<IntegrationProductResponse> integrationResponses = productIntegration.fetchProducts(name);
        List<ProductResponse> all = productMapper.toResponseList(integrationResponses);
        PaginatedResponse<ProductResponse> paginated = PaginatedResponse.of(all, page, size);
        log.info("ProductServiceImpl#listProductsPaginated: returning page {}/{} ({} total elements)",
                page, paginated.getTotalPages(), paginated.getTotalElements());
        return paginated;
    }

    @Override
    public ProductDetailResponse getProductDetail(Long id) {
        log.info("ProductServiceImpl#getProductDetail: fetching product id={}", id);
        IntegrationProductResponse integration = productIntegration.fetchProductById(id);
        ProductDetailResponse detail = productMapper.toDetailResponse(integration);
        log.info("ProductServiceImpl#getProductDetail: returning detail for product id={}", id);
        return detail;
    }

}
