package com.sparta.twotwo.ai.dto;

import com.sparta.twotwo.enums.AIRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIRequestLogResponseDto {
    private UUID id;
    private String requestText;
    private String responseText;
    private AIRequestStatus status;
}