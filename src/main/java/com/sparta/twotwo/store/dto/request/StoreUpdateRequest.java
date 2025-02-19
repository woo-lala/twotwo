package com.sparta.twotwo.store.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class StoreUpdateRequest {

    @Size(max = 50, message = "가게명은 50자를 초과할 수 없습니다.")
    public final String name;
    private final Long memberId;

    @Valid
    private final AddressUpdateRequest address;
    private final UUID categoryId;
    private final String imageUrl;
    private final Long minOrderPrice;
    private final LocalTime operationStartedAt;
    private final LocalTime operationClosedAt;
}
