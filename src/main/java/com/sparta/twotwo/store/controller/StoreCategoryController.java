package com.sparta.twotwo.store.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.store.dto.request.StoreCategoryRequestDto;
import com.sparta.twotwo.store.dto.request.StoreCreateRequestDto;
import com.sparta.twotwo.store.dto.response.StoreCategoryResponseDto;
import com.sparta.twotwo.store.entity.StoreCategory;
import com.sparta.twotwo.store.service.StoreCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreCategoryController {

    private final StoreCategoryService storeCategoryService;

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<StoreCategoryResponseDto>> saveCategory(
            @Valid @RequestBody StoreCategoryRequestDto request
    ) throws Exception {

        StoreCategory storeCategory = storeCategoryService.saveCategory(
                request.getName()
        );
        return ResponseEntity.ok(ApiResponse.success(StoreCategoryResponseDto.from(storeCategory)));
    }


}
