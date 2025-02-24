package com.sparta.twotwo.store.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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