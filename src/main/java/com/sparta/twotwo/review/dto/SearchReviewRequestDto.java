package com.sparta.twotwo.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchReviewRequestDto {
    @Min(value = 1, message = "평점은 최소 1 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 최대 5까지 입력할 수 있습니다.")
    private Integer rating;
    private String title;
    private String content;
    private String nickname;
    // 상점 검색 기능 추가
}
