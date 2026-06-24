package com.bff.application.controller;

import com.bff.application.exception.NotFoundException;
import com.bff.application.exception.ValidationException;
import com.bff.application.facade.ProductFacade;
import com.bff.application.model.dto.response.PaginatedResponse;
import com.bff.application.model.dto.response.ProductDetailResponse;
import com.bff.application.model.dto.response.ProductResponse;
import com.bff.application.enums.ProductStatusEnum;
import com.bff.application.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(com.bff.application.handler.GlobalExceptionHandler.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductFacade productFacade;

    private ProductResponse productResponse;
    private ProductDetailResponse productDetailResponse;

    @BeforeEach
    void setUp() {
        productResponse = ProductResponse.builder()
                .id(1L)
                .name("Laptop Pro 15")
                .price(2499.99)
                .status(ProductStatusEnum.DONE)
                .build();

        productDetailResponse = ProductDetailResponse.builder()
                .id(1L)
                .name("Laptop Pro 15")
                .description("High-performance laptop")
                .price(2499.99)
                .status(ProductStatusEnum.DONE)
                .createdAt("2024-01-10T08:00:00")
                .build();
    }

    @Test
    void listProducts_shouldReturn200WithProducts() throws Exception {
        when(productService.listProducts(null)).thenReturn(List.of(productResponse));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Laptop Pro 15"));

        verify(productFacade).validateListParams(null);
    }

    @Test
    void listProducts_withNameFilter_shouldPassNameToService() throws Exception {
        when(productService.listProducts("Laptop")).thenReturn(List.of(productResponse));

        mockMvc.perform(get("/api/v1/products").param("name", "Laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Laptop Pro 15"));

        verify(productService).listProducts("Laptop");
    }

    @Test
    void listProducts_whenFacadeThrowsValidation_shouldReturn400() throws Exception {
        doThrow(new ValidationException("'name' must not exceed 100 characters"))
                .when(productFacade).validateListParams(any());

        mockMvc.perform(get("/api/v1/products").param("name", "x".repeat(101)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.ok").value(false))
                .andExpect(jsonPath("$.error.code").value("ERR-009"));
    }

    @Test
    void listProductsPaginated_shouldReturn200WithPagination() throws Exception {
        PaginatedResponse<ProductResponse> paginated = PaginatedResponse.<ProductResponse>builder()
                .content(List.of(productResponse))
                .page(0).size(10).totalElements(1).totalPages(1).last(true)
                .build();
        when(productService.listProductsPaginated(0, 10, null)).thenReturn(paginated);

        mockMvc.perform(get("/api/v1/products/paginated"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.content[0].name").value("Laptop Pro 15"));
    }

    @Test
    void getProductDetail_shouldReturn200WithDetail() throws Exception {
        when(productService.getProductDetail(1L)).thenReturn(productDetailResponse);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.description").value("High-performance laptop"));

        verify(productFacade).validateDetailParams(1L);
    }

    @Test
    void getProductDetail_whenNotFound_shouldReturn404() throws Exception {
        when(productService.getProductDetail(99L))
                .thenThrow(new NotFoundException("Product not found with id: 99"));

        mockMvc.perform(get("/api/v1/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.ok").value(false))
                .andExpect(jsonPath("$.error.code").value("ERR-002"));
    }

}
