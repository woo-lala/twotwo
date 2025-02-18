package com.sparta.twotwo.store.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.store.dto.response.StoreResponse;
import com.sparta.twotwo.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    //전체 가게 조회
    @GetMapping("/stores")
    public ResponseEntity<ApiResponse<Page<StoreResponse>>> getAllStores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(storeService.getAllStores(pageable).map(StoreResponse::from)));
    }

    @GetMapping("/stores/{category_id}")
    public ResponseEntity<ApiResponse<Page<StoreResponse>>> getStoresByCategory(
            @PathVariable("category_id") UUID categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(storeService.getStoresByCategory(categoryId, pageable).map(StoreResponse::from)));
    }


}
