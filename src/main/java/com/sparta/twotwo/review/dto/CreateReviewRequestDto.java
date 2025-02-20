package com.sparta.twotwo.review.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequestDto {
    private UUID orderId;
    @NotNull(message = "평점을 입력해주세요.")
    @Min(value = 1, message = "평점은 최소 1 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 최대 5까지 입력할 수 있습니다.")
    private Integer rating;
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 50, message = "제목은 50자까지 입력할 수 있습니다.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 1, max = 300, message = "내용은 300자까지 입력할 수 있습니다.")
    private String content;
}
