package com.sparta.twotwo.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class StoreProductResponseDto {
    private UUID storeId;
    private List<ProductListResponseDto> products;
}