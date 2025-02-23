package com.sparta.twotwo.store.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
public class StoreCreateRequestDto {

    @NotNull(message = "가게명을 작성해주세요.")
    @Size(max = 50, message = "가게명은 50자를 초과할 수 없습니다.")
    private final String name;

    @NotNull
    private final Long memberId;

    @Valid
    private final AddressRequestDto address;

    @NotNull(message = "카테고리를 선택해주세요.")
    private final UUID categoryId;

    private final String imageUrl;

    private final Long minOrderPrice;

    @NotNull(message = "운영 시작 시간을 작성해주세요.")
    private final LocalTime operationStartedAt;

    @NotNull(message = "운영 종료 시간을 작성해주세요.")
    private final LocalTime operationClosedAt;

    @Builder
    public StoreCreateRequestDto(String name, Long memberId, AddressRequestDto address, UUID categoryId, String imageUrl, Long minOrderPrice, LocalTime operationStartedAt, LocalTime operationClosedAt) {
        this.name = name;
        this.memberId = memberId;
        this.address = address;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.minOrderPrice = minOrderPrice;
        this.operationStartedAt = operationStartedAt;
        this.operationClosedAt = operationClosedAt;
    }

}
