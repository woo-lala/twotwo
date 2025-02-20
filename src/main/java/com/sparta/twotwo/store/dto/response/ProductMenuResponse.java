package com.sparta.twotwo.store.dto.response;

import com.sparta.twotwo.product.dto.ProductResponseDto;
import com.sparta.twotwo.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProductMenuResponse {

    private final UUID id;
    private final String productName;
    private final UUID categoryId;
    private final String description;
    private final int price;
    private final String imageUrl;
    private final boolean isHidden;

    public static ProductMenuResponse from(Product product) {
        return new ProductMenuResponse(
                product.getId(),
                product.getProductName(),
                product.getCategoryId(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getIsHidden()
        );
    }

}
