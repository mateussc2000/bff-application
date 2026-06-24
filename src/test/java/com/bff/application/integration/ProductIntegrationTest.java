package com.bff.application.integration;

import com.bff.application.client.ProductFeignClient;
import com.bff.application.exception.IntegrationException;
import com.bff.application.integration.impl.ProductIntegrationImpl;
import com.bff.application.model.dto.integration.IntegrationProductResponse;
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
class ProductIntegrationTest {

    @Mock
    private ProductFeignClient productFeignClient;

    @InjectMocks
    private ProductIntegrationImpl productIntegration;

    private IntegrationProductResponse integrationResponse;

    @BeforeEach
    void setUp() {
        integrationResponse = new IntegrationProductResponse(
                1L, "Laptop Pro 15", "High-performance laptop",
                2499.99, "DONE", "2024-01-10T08:00:00", null);
    }

    @Test
    void fetchProducts_shouldReturnListFromFeignClient() {
        when(productFeignClient.getProducts(null)).thenReturn(List.of(integrationResponse));

        List<IntegrationProductResponse> result = productIntegration.fetchProducts(null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Laptop Pro 15");
        verify(productFeignClient).getProducts(null);
    }

    @Test
    void fetchProducts_withName_shouldPassNameToClient() {
        when(productFeignClient.getProducts("Laptop")).thenReturn(List.of(integrationResponse));

        List<IntegrationProductResponse> result = productIntegration.fetchProducts("Laptop");

        assertThat(result).hasSize(1);
        verify(productFeignClient).getProducts("Laptop");
    }

    @Test
    void fetchProducts_whenClientThrows_shouldWrapInIntegrationException() {
        when(productFeignClient.getProducts(null))
                .thenThrow(new RuntimeException("Connection refused"));

        assertThatThrownBy(() -> productIntegration.fetchProducts(null))
                .isInstanceOf(IntegrationException.class)
                .hasMessageContaining("System 1");
    }

    @Test
    void fetchProductById_shouldReturnProductFromFeignClient() {
        when(productFeignClient.getProductById(1L)).thenReturn(integrationResponse);

        IntegrationProductResponse result = productIntegration.fetchProductById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.price()).isEqualTo(2499.99);
        verify(productFeignClient).getProductById(1L);
    }

    @Test
    void fetchProductById_whenClientThrows_shouldWrapInIntegrationException() {
        when(productFeignClient.getProductById(99L))
                .thenThrow(new RuntimeException("Not found"));

        assertThatThrownBy(() -> productIntegration.fetchProductById(99L))
                .isInstanceOf(IntegrationException.class)
                .hasMessageContaining("System 1");
    }

}
