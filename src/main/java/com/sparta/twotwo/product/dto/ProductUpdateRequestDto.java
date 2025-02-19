package com.sparta.twotwo.product.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductUpdateRequestDto {
    private UUID categoryId;
    private UUID descriptionId;
    private String description;
    private String productName;
    private int price;
    private String imageUrl;
    private boolean isHidden;
}