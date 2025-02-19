package com.sparta.twotwo.product.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.product.dto.*;
import com.sparta.twotwo.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.ok(ApiResponse.success("상품이 성공적으로 등록되었습니다.", responseDto));
    }

    @GetMapping("/stores/{storeId}/products")
    public ResponseEntity<ApiResponse<List<ProductListResponseDto>>> getProductsByStore(@PathVariable UUID storeId) {
        List<ProductListResponseDto> responseDto = productService.getProductsByStoreId(storeId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable UUID productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @PutMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductUpdateResponseDto>> updateProduct(
            @PathVariable UUID productId,
            @RequestBody @Valid ProductUpdateRequestDto requestDto) {
        ProductUpdateResponseDto responseDto = productService.updateProduct(productId, requestDto);
        return ResponseEntity.ok(ApiResponse.success("상품이 성공적으로 수정되었습니다.", responseDto));
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductDeleteResponseDto>> deleteProduct(@PathVariable UUID productId) {
        ProductDeleteResponseDto responseDto = productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResponse.success("상품이 성공적으로 삭제되었습니다.", responseDto));
    }
}
