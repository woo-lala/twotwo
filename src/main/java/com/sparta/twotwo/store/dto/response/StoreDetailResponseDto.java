package com.sparta.twotwo.store.dto.response;

import com.sparta.twotwo.address.dto.response.AddressResponseDto;
import com.sparta.twotwo.product.dto.ProductMenuResponseDto;
import com.sparta.twotwo.store.entity.Store;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class StoreDetailResponseDto {

    private final UUID id;
    private final String name;
    private final String imageUrl;
    private final Long memberId;
    private final AddressResponseDto address;
    private final Long minOrderPrice;
    private final List<ProductMenuResponseDto> products;
    private final LocalTime operationStartedAt;
    private final LocalTime operationClosedAt;
    private final BigDecimal rating;
    private final Integer reviewCount;

    public static StoreDetailResponseDto from(Store store) {
        return new StoreDetailResponseDto(
                store.getId(),
                store.getName(),
                store.getImageUrl(),
                store.getMember().getMember_id(),
                AddressResponseDto.from(store.getAddress()),
                store.getMinOrderPrice(),
                store.getProducts().stream().map(ProductMenuResponseDto::from).collect(Collectors.toList()),
                store.getOperationStartedAt(),
                store.getOperationClosedAt(),
                store.getRating(),
                store.getReviewCount()
        );
    }

}
