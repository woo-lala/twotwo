package com.sparta.twotwo.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AreaRequestDto {

    @NotBlank(message = "시도를 작성해주세요.")
    private final String sido;

    @NotBlank(message = "시군구를 작성해주세요.")
    private final String sigg;

    @NotBlank(message = "읍면동을 작성해주세요.")
    private final String emd;

    @NotNull(message = "행정구역 코드를 작성해주세요.")
    private final String admCode;

    @NotNull(message = "우편번호를 작성해주세요.")
    private final String zipNum;
}
