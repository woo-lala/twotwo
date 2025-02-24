package com.sparta.twotwo.store.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
public class StoreSearchRequestDto {

    private String categoryName;
    private String storeName;

    private SortType sortType = SortType.CREATED_AT_DESC;

    public enum SortType {
        CREATED_AT_DESC,
        UPDATED_AT_DESC,
    }
    @Builder
    private StoreSearchRequestDto(String categoryName, String storeName) {
        this.categoryName = categoryName;
        this.storeName = storeName;
    }
}
