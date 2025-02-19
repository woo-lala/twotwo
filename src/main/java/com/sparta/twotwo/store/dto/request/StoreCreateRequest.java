package com.sparta.twotwo.store.dto.request;

import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.StoreCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StoreCreateRequest {

    @NotBlank(message = "가게명을 작성해주세요.")
    @Size(max = 50, message = "가게명은 50자를 초과할 수 없습니다.")
    public final String name;

    @NotNull
    private final UUID memberId;

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

    public StoreCreateRequest(String name, UUID memberId, AddressRequest address, UUID categoryId, String imageUrl, LocalTime operationStartedAt, LocalTime operationClosedAt) {

        this.name = name;
        this.memberId = memberId;
        this.address = address;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.operationStartedAt = operationStartedAt;
        this.operationClosedAt = operationClosedAt;
    }
}
