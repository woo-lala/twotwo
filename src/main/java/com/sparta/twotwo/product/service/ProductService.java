package com.sparta.twotwo.product.service;

import com.sparta.twotwo.ai.entity.AIRequestLog;
import com.sparta.twotwo.ai.service.AIService;
import com.sparta.twotwo.auth.util.SecurityUtil;
import com.sparta.twotwo.enums.AIRequestStatus;
import com.sparta.twotwo.product.dto.*;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.product.repository.ProductRepository;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final AIService aiService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private Long authenticateMember() {
        Long memberId = SecurityUtil.getMemberIdFromSecurityContext();
        if (memberId == null) {
            throw new IllegalStateException("로그인한 사용자만 접근할 수 있습니다.");
        }
        return memberId;
    }

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다: " + requestDto.getStoreId()));

        Long createdBy = authenticateMember();

        if (requestDto.getPrice() < 0) {
            throw new IllegalArgumentException("상품 가격은 0 이상이어야 합니다.");
        }

        Product product = new Product();
        product.setStore(store);
        product.setProductName(requestDto.getProductName());
        product.setPrice(requestDto.getPrice());
        product.setImageUrl(requestDto.getImageUrl());
        product.setCreatedBy(createdBy);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        AIRequestLog aiRequestLog = aiService.generateProductDescription(savedProduct);

        if (aiRequestLog != null && aiRequestLog.getStatus() == AIRequestStatus.SUCCESS) {
            savedProduct.setDescriptionLog(aiRequestLog);
            savedProduct.setDescription(aiRequestLog.getResponseText());
            savedProduct = productRepository.save(savedProduct);
        }

        return ProductResponseDto.builder()
                .productId(savedProduct.getId())
                .storeId(savedProduct.getStore().getId())
                .description(savedProduct.getDescription())
                .productName(savedProduct.getProductName())
                .price(savedProduct.getPrice())
                .imageUrl(savedProduct.getImageUrl())
                .createdAt(savedProduct.getCreatedAt().format(FORMATTER))
                .createdBy(savedProduct.getCreatedBy())
                .build();
    }


    @Transactional(readOnly = true)
    public List<ProductListResponseDto> getProductsByStoreId(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다: " + storeId));

        return productRepository.findByStoreAndIsDeletedFalse(store).stream()
                .map(product -> ProductListResponseDto.builder()
                        .productId(product.getId())
                        .descriptionId(product.getDescriptionLog() != null ? product.getDescriptionLog().getId() : null)
                        .description(product.getDescription())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .imageUrl(product.getImageUrl())
                        .createdAt(product.getCreatedAt().format(FORMATTER))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(UUID productId) {
        Product product = productRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + productId));

        return ProductResponseDto.builder()
                .productId(product.getId())
                .storeId(product.getStore().getId())
                .descriptionId(product.getDescriptionLog() != null ? product.getDescriptionLog().getId() : null)
                .description(product.getDescription())
                .productName(product.getProductName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt().format(FORMATTER))
                .createdBy(product.getCreatedBy())
                .updatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().format(FORMATTER) : null)
                .updatedBy(product.getUpdatedBy() != null ? product.getUpdatedBy() : null)
                .build();

    }

    @Transactional
    public ProductUpdateResponseDto updateProduct(UUID productId, ProductUpdateRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + productId));

        Long updatedBy = authenticateMember();

        if (requestDto.getProductName() != null) {
            product.setProductName(requestDto.getProductName());
        }

        if (requestDto.getPrice() != null) {
            if (requestDto.getPrice() < 0) {
                throw new IllegalArgumentException("상품 가격은 0 이상이어야 합니다.");
            }
            product.setPrice(requestDto.getPrice());
        }

        if (requestDto.getImageUrl() != null) {
            product.setImageUrl(requestDto.getImageUrl());
            // 요청에서 description이 포함됨 -> 사용자가 입력한 값으로 덮어씀
            // 포함X -> 기존 설명 유지
        }

        if (requestDto.getDescription() != null) {
            product.setDescription(requestDto.getDescription());
        }

        product.setUpdatedBy(updatedBy);
        product.setUpdatedAt(LocalDateTime.now());

        return ProductUpdateResponseDto.builder()
                .productId(product.getId())
                .updatedAt(product.getUpdatedAt().format(FORMATTER))
                .updatedBy(updatedBy)
                .build();
    }

    @Transactional
    public ProductDeleteResponseDto deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + productId));

        Long deletedBy = authenticateMember();

        product.setDeletedBy(deletedBy);
        product.setDeletedAt(LocalDateTime.now());
        product.setIsDeleted(true);
        product.setIsHidden(true);

        return ProductDeleteResponseDto.builder()
                .productId(product.getId())
                .deletedAt(product.getDeletedAt().format(FORMATTER))
                .deletedBy(deletedBy)
                .build();
    }
}
