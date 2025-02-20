package com.sparta.twotwo.store.dto.response;

import com.sparta.twotwo.common.auditing.BaseEntity;
import com.sparta.twotwo.store.entity.Address;
import com.sparta.twotwo.store.entity.Area;
import com.sparta.twotwo.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AddressResponse {

    private final UUID id;
    private final String roadAddress;
    private final String detailAddress;

    public static AddressResponse from(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getRoadAddress(),
                address.getDetailAddress()
        );
    }
}