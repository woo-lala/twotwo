package com.sparta.twotwo.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductUpdateRequestDto {
    private UUID descriptionId;
    private String description;
    private String productName;
    private int price;
    private String imageUrl;
    private boolean isHidden;
}