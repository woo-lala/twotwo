package com.sparta.twotwo.store.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.StoreCategory;
import com.sparta.twotwo.store.repository.StoreCategoryRepository;
import com.sparta.twotwo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;

    public Page<Store> getAllStores(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }

    public Optional<Store> getStoreDetails(UUID storeId) {
        return storeRepository.findById(storeId);
    }

    public Page<Store> getStoresByCategory(UUID categoryId, Pageable pageable) {
        Optional<StoreCategory> category = storeCategoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new TwotwoApplicationException(ErrorCode.STORE_NOT_FOUND);
        }
        return storeRepository.findStoresByCategoryId(categoryId, pageable);
    }

}


