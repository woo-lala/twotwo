package com.sparta.twotwo.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductListResponseDto {
    private UUID productId;
    private UUID categoryId;
    private UUID descriptionId;
    private String description;
    private String productName;
    private int price;
    private String imageUrl;
    private boolean isHidden;
    private String createdAt;
}