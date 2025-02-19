package com.sparta.twotwo.review.dto;

import com.sparta.twotwo.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private UUID reviewId;
    private UUID orderId;
    private String nickname;
    private String title;
    private String content;
    private int rating;
    private boolean isHidden;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReview_id();
        this.orderId = review.getOrder().getOrder_id();
        this.nickname = review.getMember().getNickname();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.isHidden = review.getIsHidden();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }
}
