package com.sparta.twotwo.product.service;

import com.sparta.twotwo.product.dto.ProductListResponseDto;
import com.sparta.twotwo.product.dto.ProductRequestDto;
import com.sparta.twotwo.product.dto.ProductResponseDto;
import com.sparta.twotwo.product.dto.StoreProductResponseDto;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setStoreId(requestDto.getStoreId());
        product.setCategoryId(requestDto.getCategoryId());
        product.setDescription(requestDto.getDescription());
        product.setProductName(requestDto.getProductName());
        product.setPrice(requestDto.getPrice());
        product.setImageUrl(requestDto.getImageUrl());
        product.setHidden(requestDto.isHidden());

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.builder()
                .productId(savedProduct.getId())
                .storeId(savedProduct.getStoreId())
                .categoryId(savedProduct.getCategoryId())
                .description(savedProduct.getDescription())
                .productName(savedProduct.getProductName())
                .price(savedProduct.getPrice())
                .imageUrl(savedProduct.getImageUrl())
                .isHidden(savedProduct.isHidden())
                .createdAt(savedProduct.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .createdBy(savedProduct.getCreatedBy())
                .build();
    }

    @Transactional(readOnly = true)
    public StoreProductResponseDto getProductsByStoreId(UUID storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        List<ProductListResponseDto> productList = new ArrayList<>();

        for (Product product : products) {
            ProductListResponseDto dto = ProductListResponseDto.builder()
                    .productId(product.getId())
                    .categoryId(product.getCategoryId())
                    .descriptionId(product.getDescriptionLog() != null ? product.getDescriptionLog().getId() : null)
                    .description(product.getDescription())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .imageUrl(product.getImageUrl())
                    .isHidden(product.isHidden())
                    .createdAt(product.getCreatedAt())
                    .build();

            productList.add(dto);
        }

        return StoreProductResponseDto.builder()
                .storeId(storeId)
                .products(productList)
                .build();
    }
}