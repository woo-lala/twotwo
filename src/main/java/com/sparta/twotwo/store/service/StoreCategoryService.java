package com.sparta.twotwo.store.service;

import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.StoreCategory;
import com.sparta.twotwo.store.repository.StoreCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCategoryService {

    private final StoreCategoryRepository storeCategoryRepository;

    public StoreCategory saveCategory(String reqName) {

        StoreCategory newStoreCategory = StoreCategory.builder()
                .name(reqName)
                .build();

        return storeCategoryRepository.save(newStoreCategory);
    }

}
