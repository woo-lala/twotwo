package com.sparta.twotwo.review.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.review.dto.CreateReviewRequestDto;
import com.sparta.twotwo.review.dto.ReviewResponseDto;
import com.sparta.twotwo.review.dto.UpdateReviewRequestDto;
import com.sparta.twotwo.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(
            @Valid
            @RequestBody CreateReviewRequestDto requestDto
    ) {
        ReviewResponseDto responseDto = reviewService.createReview(requestDto);
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReviewResponseDto>>> getReviews(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        Page<ReviewResponseDto> reviews = reviewService.getReviews(page-1, size, sortBy, isAsc);
        return new ResponseEntity<>(ApiResponse.success(reviews), HttpStatus.OK);
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReview(@PathVariable("review_id") UUID reviewId) {
        ReviewResponseDto review = reviewService.getReview(reviewId);
        return new ResponseEntity<>(ApiResponse.success(review), HttpStatus.OK);
    }

    @PatchMapping("/{review_id}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(
            @Valid
            @RequestBody UpdateReviewRequestDto requestDto,
            @PathVariable("review_id") UUID reviewId
            ) {
        ReviewResponseDto review = reviewService.updateReview(reviewId, requestDto);
        return new ResponseEntity<>(ApiResponse.success(review), HttpStatus.OK);
    }

    @DeleteMapping("/{review_id}")
    public void deleteReview(@PathVariable("review_id") UUID reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
