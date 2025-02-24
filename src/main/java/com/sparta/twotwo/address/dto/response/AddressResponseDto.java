package com.sparta.twotwo.address.dto.response;

import com.sparta.twotwo.store.entity.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AddressResponseDto {

    private final UUID id;
    private final String roadAddress;
    private final String detailAddress;

    public static AddressResponseDto from(Address address) {
        return new AddressResponseDto(
                address.getId(),
                address.getRoadAddress(),
                address.getDetailAddress()
        );
    }
}