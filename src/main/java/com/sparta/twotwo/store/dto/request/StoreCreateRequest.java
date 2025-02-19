package com.sparta.twotwo.store.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
public class StoreCreateRequest {

    @NotNull(message = "가게명을 작성해주세요.")
    @Size(max = 50, message = "가게명은 50자를 초과할 수 없습니다.")
    public final String name;

    @NotNull
    private final Long memberId;

    @Valid
    private final AddressRequest address;

    @NotNull(message = "카테고리를 선택해주세요.")
    private final UUID categoryId;

    private final String imageUrl;

    private final Long minOrderPrice = 0L;

    @NotNull(message = "운영 시작 시간을 작성해주세요.")
    private final LocalTime operationStartedAt;

    @NotNull(message = "운영 종료 시간을 작성해주세요.")
    private final LocalTime operationClosedAt;

    public StoreCreateRequest(String name, Long memberId, AddressRequest address, UUID categoryId, String imageUrl, LocalTime operationStartedAt, LocalTime operationClosedAt) {
        this.name = name;
        this.memberId = memberId;
        this.address = address;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.operationStartedAt = operationStartedAt;
        this.operationClosedAt = operationClosedAt;
    }
}
