package com.sparta.twotwo.store.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AddressRequest {

    @NotBlank(message = "시도를 작성해주세요.")
    private final String sido;

    @NotBlank(message = "시군구를 작성해주세요.")
    private final String sigg;

    @NotBlank(message = "읍면동을 작성해주세요.")
    private final String emd;

    @NotBlank(message = "우편번호를 작성해주세요.")
    private final String admCode;

    @NotBlank(message = "우편번호를 작성해주세요.")
    private final String zipNum;

    @NotBlank(message = "도로명주소를 작성해주세요.")
    private final String roadAddress;

    @NotBlank(message = "상세주소를 작성해주세요.")
    private final String detailAddress;


}