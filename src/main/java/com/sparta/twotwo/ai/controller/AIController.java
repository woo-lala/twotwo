package com.sparta.twotwo.ai.controller;

import com.sparta.twotwo.ai.dto.AIRequestLogResponseDto;
import com.sparta.twotwo.ai.entity.AIRequestLog;
import com.sparta.twotwo.ai.service.AIService;
import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;
    private final ProductRepository productRepository;

    @PostMapping("/generate/{productId}")
    public ResponseEntity<ApiResponse<AIRequestLogResponseDto>> generateDescription(@PathVariable UUID productId, @RequestBody String prompt) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + productId));

        AIRequestLog aiRequestLog = aiService.generateProductDescription(product, prompt);

        AIRequestLogResponseDto responseDto = AIRequestLogResponseDto.builder()
                .id(aiRequestLog.getId())
                .requestText(aiRequestLog.getRequestText())
                .responseText(aiRequestLog.getResponseText())
                .status(aiRequestLog.getStatus())
                .build();

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }
}