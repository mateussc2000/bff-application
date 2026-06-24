package com.bff.application.model.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IntegrationProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        String status,
        String createdAt,
        String updatedAt
) {}
