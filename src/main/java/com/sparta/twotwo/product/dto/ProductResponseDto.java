package com.sparta.twotwo.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값이면 JSON 응답에서 제외
public class ProductResponseDto {
    private UUID productId;
    private UUID storeId;
    private UUID descriptionId;
    private String description;
    private String productName;
    private int price;
    private String imageUrl;
    private boolean isHidden;
    private String createdAt;
    private Long createdBy;
    private String updatedAt;
    private Long updatedBy;
}
