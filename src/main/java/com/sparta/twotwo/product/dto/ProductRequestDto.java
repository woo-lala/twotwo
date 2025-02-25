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

    private String description; //설명 수동 입력 가능 (?)

    @NotBlank
    private String productName;

    @Min(0)
    private int price;

    private String imageUrl;

}