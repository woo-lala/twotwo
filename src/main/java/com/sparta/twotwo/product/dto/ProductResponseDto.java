package com.sparta.twotwo.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductResponseDto {
    private UUID productId;
    private UUID storeId;
    private UUID categoryId;
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