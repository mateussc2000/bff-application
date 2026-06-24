package com.bff.application.service;

import com.bff.application.enums.ProductStatusEnum;
import com.bff.application.exception.IntegrationException;
import com.bff.application.integration.ProductIntegration;
import com.bff.application.model.dto.integration.IntegrationProductResponse;
import com.bff.application.model.dto.response.PaginatedResponse;
import com.bff.application.model.dto.response.ProductDetailResponse;
import com.bff.application.model.dto.response.ProductResponse;
import com.bff.application.model.mapper.ProductMapper;
import com.bff.application.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductIntegration productIntegration;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private IntegrationProductResponse integrationResponse;
    private ProductResponse productResponse;
    private ProductDetailResponse productDetailResponse;

    @BeforeEach
    void setUp() {
        integrationResponse = new IntegrationProductResponse(
                1L, "Laptop Pro 15", "High-performance laptop", 2499.99,
                "DONE", "2024-01-10T08:00:00", null);

        productResponse = ProductResponse.builder()
                .id(1L).name("Laptop Pro 15").price(2499.99).status(ProductStatusEnum.DONE)
                .build();

        productDetailResponse = ProductDetailResponse.builder()
                .id(1L).name("Laptop Pro 15").description("High-performance laptop")
                .price(2499.99).status(ProductStatusEnum.DONE).createdAt("2024-01-10T08:00:00")
                .build();
    }

    @Test
    void listProducts_shouldReturnMappedProducts() {
        when(productIntegration.fetchProducts(null)).thenReturn(List.of(integrationResponse));
        when(productMapper.toResponseList(List.of(integrationResponse))).thenReturn(List.of(productResponse));

        List<ProductResponse> result = productService.listProducts(null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Laptop Pro 15");
        verify(productIntegration).fetchProducts(null);
        verify(productMapper).toResponseList(anyList());
    }

    @Test
    void listProducts_withName_shouldPassNameToIntegration() {
        when(productIntegration.fetchProducts("Laptop")).thenReturn(List.of(integrationResponse));
        when(productMapper.toResponseList(anyList())).thenReturn(List.of(productResponse));

        List<ProductResponse> result = productService.listProducts("Laptop");

        assertThat(result).hasSize(1);
        verify(productIntegration).fetchProducts("Laptop");
    }

    @Test
    void listProducts_whenIntegrationFails_shouldPropagateException() {
        when(productIntegration.fetchProducts(null))
                .thenThrow(new IntegrationException("System 1 unavailable"));

        assertThatThrownBy(() -> productService.listProducts(null))
                .isInstanceOf(IntegrationException.class)
                .hasMessageContaining("System 1 unavailable");
    }

    @Test
    void listProductsPaginated_shouldReturnCorrectPage() {
        List<ProductResponse> all = List.of(
                ProductResponse.builder().id(1L).name("A").price(10.0).build(),
                ProductResponse.builder().id(2L).name("B").price(20.0).build(),
                ProductResponse.builder().id(3L).name("C").price(30.0).build());
        when(productIntegration.fetchProducts(null)).thenReturn(List.of(integrationResponse, integrationResponse, integrationResponse));
        when(productMapper.toResponseList(anyList())).thenReturn(all);

        PaginatedResponse<ProductResponse> result = productService.listProductsPaginated(0, 2, null);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.isLast()).isFalse();
    }

    @Test
    void getProductDetail_shouldReturnDetailResponse() {
        when(productIntegration.fetchProductById(1L)).thenReturn(integrationResponse);
        when(productMapper.toDetailResponse(integrationResponse)).thenReturn(productDetailResponse);

        ProductDetailResponse result = productService.getProductDetail(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDescription()).isEqualTo("High-performance laptop");
        verify(productIntegration).fetchProductById(1L);
    }

    @Test
    void getProductDetail_whenIntegrationFails_shouldPropagateException() {
        when(productIntegration.fetchProductById(99L))
                .thenThrow(new IntegrationException("Product not found"));

        assertThatThrownBy(() -> productService.getProductDetail(99L))
                .isInstanceOf(IntegrationException.class);
    }

}
