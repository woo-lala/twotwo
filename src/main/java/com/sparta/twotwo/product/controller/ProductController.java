package com.sparta.twotwo.product.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.product.dto.*;
import com.sparta.twotwo.product.service.ProductService;
import com.sparta.twotwo.product.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final S3Service s3Service;

    @GetMapping("/presigned-url")
    public ResponseEntity<ApiResponse<PresignedUrlResponseDto>> getPresignedUrl(@RequestParam String filename) {
        String uniqueFilename = UUID.randomUUID() + "_" + filename;
        String imageKey = "img/" + uniqueFilename;
        String presignedUrl = s3Service.createPresignedUrl(imageKey);
        PresignedUrlResponseDto response = new PresignedUrlResponseDto(presignedUrl, imageKey);
        return ResponseEntity.ok(ApiResponse.success("Presigned URL 생성 성공", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.ok(ApiResponse.success("상품이 성공적으로 등록되었습니다.", responseDto));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<ApiResponse<List<ProductListResponseDto>>> getProductsByStore(@PathVariable UUID storeId) {
        List<ProductListResponseDto> responseDto = productService.getProductsByStoreId(storeId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable UUID productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductUpdateResponseDto>> updateProduct(
            @PathVariable UUID productId,
            @RequestBody @Valid ProductUpdateRequestDto requestDto) {
        ProductUpdateResponseDto responseDto = productService.updateProduct(productId, requestDto);
        return ResponseEntity.ok(ApiResponse.success("상품이 성공적으로 수정되었습니다.", responseDto));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDeleteResponseDto>> deleteProduct(@PathVariable UUID productId) {
        ProductDeleteResponseDto responseDto = productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResponse.success("상품이 성공적으로 삭제되었습니다.", responseDto));
    }
}
