package com.sparta.twotwo.product.dto;

import com.sparta.twotwo.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProductMenuResponseDto {

    private final UUID id;
    private final String productName;
    private final String description;
    private final int price;
    private final String imageUrl;
    private final boolean isHidden;

    public static ProductMenuResponseDto from(Product product) {
        return new ProductMenuResponseDto(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getIsHidden()
        );
    }

}
