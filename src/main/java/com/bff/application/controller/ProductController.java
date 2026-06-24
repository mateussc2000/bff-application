package com.bff.application.controller;

import com.bff.application.facade.ProductFacade;
import com.bff.application.model.dto.response.PaginatedResponse;
import com.bff.application.model.dto.response.ProductDetailResponse;
import com.bff.application.model.dto.response.ProductResponse;
import com.bff.application.model.dto.response.ResponseTemplate;
import com.bff.application.service.ProductService;
import com.bff.application.utils.DateUtils;
import com.bff.application.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductFacade productFacade;

    @GetMapping
    public ResponseEntity<ResponseTemplate<List<ProductResponse>>> listProducts(
            @RequestParam(required = false) String name) {
        long start = System.nanoTime();
        log.info("Receiving call for ProductController#listProducts: name={}", name);

        productFacade.validateListParams(name);
        List<ProductResponse> result = productService.listProducts(name);

        log.info("Ending requisition for ProductController#listProducts: result={} items, timeSpent={}ms",
                result.size(), DateUtils.elapsedMillis(start));
        return ResponseUtils.ok(result);
    }

    @GetMapping("/paginated")
    public ResponseEntity<ResponseTemplate<PaginatedResponse<ProductResponse>>> listProductsPaginated(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        long start = System.nanoTime();
        log.info("Receiving call for ProductController#listProductsPaginated: page={}, size={}, name={}",
                page, size, name);

        productFacade.validatePaginatedParams(page, size, name);
        PaginatedResponse<ProductResponse> result = productService.listProductsPaginated(page, size, name);

        log.info("Ending requisition for ProductController#listProductsPaginated: result={} items (page {}/{}), timeSpent={}ms",
                result.getTotalElements(), page, result.getTotalPages(), DateUtils.elapsedMillis(start));
        return ResponseUtils.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ProductDetailResponse>> getProductDetail(
            @PathVariable Long id) {
        long start = System.nanoTime();
        log.info("Receiving call for ProductController#getProductDetail: id={}", id);

        productFacade.validateDetailParams(id);
        ProductDetailResponse result = productService.getProductDetail(id);

        log.info("Ending requisition for ProductController#getProductDetail: result=name='{}', timeSpent={}ms",
                result.getName(), DateUtils.elapsedMillis(start));
        return ResponseUtils.ok(result);
    }

}

