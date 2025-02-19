package com.sparta.twotwo.store.dto.response;

import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.StoreCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class StoreDetailResponse {

    private final UUID id;
    private final String name;
    private final String imageUrl;
//    private final Member member;
    private final AddressResponse address;
    private final Long minOrderPrice;
//    private final List<ProductResponse> products;
    private final LocalTime operationStartedAt;
    private final LocalTime operationClosedAt;
    private final BigDecimal rating;
    private final Integer reviewCount;

    public static StoreDetailResponse from(Store store) {
        return new StoreDetailResponse(
                store.getId(),
                store.getName(),
                store.getImageUrl(),
//                store.getMember(),
                AddressResponse.from(store.getAddress()),
                store.getMinOrderPrice(),
                store.getOperationStartedAt(),
                store.getOperationClosedAt(),
                store.getRating(),
                store.getReviewCount()
        );
    }

}
