package com.sparta.twotwo.store.controller;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.store.dto.request.StoreCreateRequest;
import com.sparta.twotwo.store.dto.request.StoreUpdateRequest;
import com.sparta.twotwo.store.dto.response.StoreDetailResponse;
import com.sparta.twotwo.store.dto.response.StoreResponse;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.service.StoreService;
import jakarta.validation.Valid;
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

    /**
     * 가게 전체 조회
     * 사용자 권한 (Master, Manager, Owner, Customer)
     */
    @GetMapping("/stores")
    public ResponseEntity<ApiResponse<Page<StoreResponse>>> getAllStores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(storeService.getAllStores(pageable).map(StoreResponse::from)));
    }

    /**
     * 가게 상세 조회
     * 사용자 권한 (Master, Manager, Owner, Customer)
     */
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<StoreDetailResponse>> getStoreDetails(
            @PathVariable UUID storeId
    ) {
        return ResponseEntity.ok(ApiResponse.success(StoreDetailResponse.from(storeService.getStoreDetails(storeId).orElseThrow(
                () -> new TwotwoApplicationException(ErrorCode.STORE_BAD_REQUEST)))
        ));
    }

    /**
     * 카테고리별 가게 조회
     * 사용자 권한 (Master, Manager, Owner, Customer)
     */
    @GetMapping("/categories/{categoryId}/stores")
    public ResponseEntity<ApiResponse<Page<StoreResponse>>> getStoresByCategory(
            @PathVariable UUID categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(storeService.getStoresByCategory(categoryId, pageable).map(StoreResponse::from)));
    }

    /**
     * 가게 등록
     * 사용자 권한 (Master, Manager)
     */
    @PostMapping("/stores")
    public ResponseEntity<ApiResponse<StoreDetailResponse>> createStore(
            @Valid @RequestBody StoreCreateRequest request
    ) throws Exception {
        Store store = storeService.saveStore(request);
        return ResponseEntity.ok(ApiResponse.success(StoreDetailResponse.from(store)));
    }

    /**
     * 가게 수정
     * 사용자 권한 (Master, Manager)
     */
    @PatchMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<StoreDetailResponse>> updateStore(
            @PathVariable UUID storeId,
            @Valid @RequestBody StoreUpdateRequest request
    ) throws Exception {
        Store store = storeService.updateStore(storeId,request);
        return ResponseEntity.ok(ApiResponse.success(StoreDetailResponse.from(store)));
    }

    /**
     * 가게 삭제
     * 사용자 권한 (Master, Manager)
     */
    @DeleteMapping("/stores/{storeId}")
    public ResponseEntity<Void> deleteStore(
            @PathVariable UUID storeId
    ) throws Exception {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }

}
