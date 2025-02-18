package com.sparta.twotwo.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequestDto {
    private UUID orderId;
    private Long memberId;
    private Integer rating;
    private String title;
    private String content;
}
