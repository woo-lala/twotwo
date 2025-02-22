package com.sparta.twotwo.store.controller;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.store.dto.request.StoreCreateRequestDto;
import com.sparta.twotwo.store.dto.request.StoreUpdateRequestDto;
import com.sparta.twotwo.store.dto.response.StoreDetailResponseDto;
import com.sparta.twotwo.store.dto.response.StoreResponseDto;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.mapper.StoreMapper;
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
    private final StoreMapper storeMapper;

    /**
     * 가게 전체 조회
     * 사용자 권한 (Master, Manager, Owner, Customer)
     */
    @GetMapping("/stores")
    public ResponseEntity<ApiResponse<Page<StoreResponseDto>>> getAllStores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(storeService.getAllStores(pageable).map(StoreResponseDto::from)));
    }

    /**
     * 가게 상세 조회
     * 사용자 권한 (Master, Manager, Owner, Customer)
     */
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<StoreDetailResponseDto>> getStoreDetails(
            @PathVariable UUID storeId
    ) {
        return ResponseEntity.ok(ApiResponse.success(StoreDetailResponseDto.from(storeService.getStoreDetails(storeId).orElseThrow(
                () -> new TwotwoApplicationException(ErrorCode.STORE_BAD_REQUEST)))
        ));
    }

    /**
     * 카테고리별 가게 조회
     * 사용자 권한 (Master, Manager, Owner, Customer)
     */
    @GetMapping("/categories/{categoryId}/stores")
    public ResponseEntity<ApiResponse<Page<StoreResponseDto>>> getStoresByCategory(
            @PathVariable UUID categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(storeService.getStoresByCategory(categoryId, pageable).map(StoreResponseDto::from)));
    }

    /**
     * 가게 등록
     * 사용자 권한 (Master, Manager)
     */
    @PostMapping("/stores")
    public ResponseEntity<ApiResponse<StoreDetailResponseDto>> createStore(
            @Valid @RequestBody StoreCreateRequestDto request
    ) throws Exception {
        Store reqStore = storeMapper.toStore(request);
        Store store = storeService.saveStore(reqStore);
        return ResponseEntity.ok(ApiResponse.success(StoreDetailResponseDto.from(store)));
    }

    /**
     * 가게 수정
     * 사용자 권한 (Master, Manager, Owner)
     */
    @PatchMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<StoreDetailResponseDto>> updateStore(
            @PathVariable UUID storeId,
            @Valid @RequestBody StoreUpdateRequestDto request
    ) throws Exception {
        Store reqStore = storeMapper.toStore(request);
        Store store = storeService.updateStore(storeId, reqStore);
        return ResponseEntity.ok(ApiResponse.success(StoreDetailResponseDto.from(store)));
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
