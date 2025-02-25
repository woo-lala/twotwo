package com.sparta.twotwo.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateReviewRequestDto {
    @Min(value = 1, message = "평점은 최소 1 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 최대 5까지 입력할 수 있습니다.")
    private Integer rating;
    @Size(min = 1, max = 50, message = "제목은 50자까지 입력할 수 있습니다.")
    private String title;
    @Size(min = 1, max = 300, message = "내용은 300자까지 입력할 수 있습니다.")
    private String content;
    public boolean isHidden;
}
