package com.sparta.twotwo.product.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.product.dto.ProductRequestDto;
import com.sparta.twotwo.product.dto.ProductResponseDto;
import com.sparta.twotwo.product.dto.StoreProductResponseDto;
import com.sparta.twotwo.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.ok(ApiResponse.success("상품이 성공적으로 등록되었습니다.", responseDto));
    }

    @GetMapping("/stores/{storeId}/products")
    public ResponseEntity<ApiResponse<StoreProductResponseDto>> getProductsByStore(@PathVariable UUID storeId) {
        StoreProductResponseDto responseDto = productService.getProductsByStoreId(storeId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable UUID productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return ResponseEntity.ok(ApiResponse.success(product));
    }
}
