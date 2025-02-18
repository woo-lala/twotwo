package com.sparta.twotwo.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductRequestDto {
    @NotNull
    private UUID storeId;

    @NotNull
    private UUID categoryId;

    private UUID descriptionId;

    private String description;

    @NotBlank
    private String productName;

    @Min(0)
    private int price;

    private String imageUrl;

    private boolean isHidden;
}