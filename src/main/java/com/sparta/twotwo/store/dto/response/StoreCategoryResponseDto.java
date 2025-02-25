package com.sparta.twotwo.store.dto.response;

import com.sparta.twotwo.store.entity.StoreCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreCategoryResponseDto {

    private final String name;

    public static StoreCategoryResponseDto from(StoreCategory storeCategory) {
        return new StoreCategoryResponseDto(
                storeCategory.getName()
        );
    }

}

