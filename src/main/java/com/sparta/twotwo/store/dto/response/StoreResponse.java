package com.sparta.twotwo.store.dto.response;

import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Store;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class StoreResponse {

    private final UUID id;
    private final String imageUrl;
    private final String name;
    private final Long minOrderPrice;
//    private final List<ProductResponse> products;
    private final BigDecimal rating;
    private final Integer reviewCount;

    public static StoreResponse from(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getImageUrl(),
                store.getName(),
                store.getMinOrderPrice(),
                store.getRating(),
                store.getReviewCount()
        );
    }


}
