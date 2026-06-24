package com.bff.application.controller.mock;

import com.bff.application.model.dto.integration.IntegrationProductResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simulates an external downstream service (System 1).
 * This controller exists purely for demonstration purposes so that
 * the FeignClient can exercise the full integration chain locally.
 */
@RestController
@RequestMapping("/mock/api")
public class MockExternalProductController {

    private static final Map<Long, IntegrationProductResponse> PRODUCTS = Map.of(
            1L, new IntegrationProductResponse(1L, "Laptop Pro 15", "High-performance laptop", 2499.99, "DONE", "2024-01-10T08:00:00", "2024-03-15T12:30:00"),
            2L, new IntegrationProductResponse(2L, "Wireless Mouse", "Ergonomic wireless mouse", 49.90, "DELIVERED", "2024-01-12T09:00:00", null),
            3L, new IntegrationProductResponse(3L, "USB-C Hub 7-in-1", "Multi-port USB-C hub", 89.99, "DELIVERED", "2024-01-20T10:00:00", null),
            4L, new IntegrationProductResponse(4L, "Mechanical Keyboard", "RGB mechanical keyboard", 199.99, "LATE", "2024-02-01T11:00:00", null),
            5L, new IntegrationProductResponse(5L, "Monitor 27\"", "4K IPS display 27 inch", 799.99, "CANCELLED", "2024-02-10T14:00:00", null)
    );

    @GetMapping("/products")
    public List<IntegrationProductResponse> getProducts(
            @RequestParam(required = false) String name) {
        List<IntegrationProductResponse> all = List.copyOf(PRODUCTS.values());
        if (name == null || name.isBlank()) {
            return all;
        }
        return all.stream()
                .filter(p -> p.name().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public IntegrationProductResponse getProductById(@PathVariable Long id) {
        IntegrationProductResponse product = PRODUCTS.get(id);
        if (product == null) {
            throw new com.bff.application.exception.NotFoundException(
                    "Product not found with id: " + id);
        }
        return product;
    }

}
