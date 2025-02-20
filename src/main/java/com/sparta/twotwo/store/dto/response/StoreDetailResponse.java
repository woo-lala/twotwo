package com.sparta.twotwo.store.dto.response;

import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.product.dto.ProductResponseDto;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.StoreCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class StoreDetailResponse {

    private final UUID id;
    private final String name;
    private final String imageUrl;
    private final Long memberId;
    private final AddressResponse address;
    private final Long minOrderPrice;
    private final List<ProductMenuResponse> products;
    private final LocalTime operationStartedAt;
    private final LocalTime operationClosedAt;
    private final BigDecimal rating;
    private final Integer reviewCount;

    public static StoreDetailResponse from(Store store) {
        return new StoreDetailResponse(
                store.getId(),
                store.getName(),
                store.getImageUrl(),
                store.getMember().getMember_id(),
                AddressResponse.from(store.getAddress()),
                store.getMinOrderPrice(),
                store.getProducts().stream().map(ProductMenuResponse::from).collect(Collectors.toList()),
                store.getOperationStartedAt(),
                store.getOperationClosedAt(),
                store.getRating(),
                store.getReviewCount()
        );
    }

}
