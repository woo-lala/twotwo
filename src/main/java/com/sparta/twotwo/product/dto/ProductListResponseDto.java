package com.sparta.twotwo.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductListResponseDto {
    private UUID productId;
    private UUID descriptionId;
    private String description;
    private String productName;
    private int price;
    private String imageUrl;
    private String createdAt;
}
