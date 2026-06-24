package com.bff.application.service;

import com.bff.application.model.dto.response.PaginatedResponse;
import com.bff.application.model.dto.response.ProductDetailResponse;
import com.bff.application.model.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> listProducts(String name);

    PaginatedResponse<ProductResponse> listProductsPaginated(int page, int size, String name);

    ProductDetailResponse getProductDetail(Long id);

}
