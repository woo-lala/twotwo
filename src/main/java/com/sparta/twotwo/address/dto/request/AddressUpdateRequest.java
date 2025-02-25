package com.sparta.twotwo.address.dto.request;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class AddressUpdateRequest {

    @Valid
    private final AreaUpdateRequestDto area;

    private final String roadAddress;

    private final String detailAddress;

}