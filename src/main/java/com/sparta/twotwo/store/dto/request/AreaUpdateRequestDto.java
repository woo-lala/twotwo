package com.sparta.twotwo.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AreaUpdateRequestDto {

    private final String sido;

    private final String sigg;

    private final String emd;

    private final String admCode;

    private final String zipNum;
}
