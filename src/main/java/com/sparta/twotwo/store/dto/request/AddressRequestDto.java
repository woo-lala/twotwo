package com.sparta.twotwo.store.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class AddressRequestDto {

    @Valid
    private final AreaRequestDto area;

    @NotNull(message = "도로명주소를 작성해주세요.")
    private final String roadAddress;

    @NotNull(message = "상세주소를 작성해주세요.")
    private final String detailAddress;

}

