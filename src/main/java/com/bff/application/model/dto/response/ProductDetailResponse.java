package com.bff.application.model.dto.response;

import com.bff.application.enums.ProductStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private ProductStatusEnum status;
    private String createdAt;
    private String updatedAt;

}
