package com.sparta.twotwo.review.dto;

import com.sparta.twotwo.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReview_id();
        this.orderId = review.getOrder().getOrder_id();
        this.nickname = review.getMember().getNickname();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
    }
}
