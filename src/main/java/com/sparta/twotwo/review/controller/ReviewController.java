package com.sparta.twotwo.review.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.review.dto.CreateReviewRequestDto;
import com.sparta.twotwo.review.dto.ReviewResponseDto;
import com.sparta.twotwo.review.dto.SearchReviewRequestDto;
import com.sparta.twotwo.review.dto.UpdateReviewRequestDto;
import com.sparta.twotwo.review.entity.Review;
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
        Review review = reviewService.createReview(requestDto);

        ReviewResponseDto responseDto = new ReviewResponseDto(review);
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReviewResponseDto>>> getReviews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean isAsc,
            @Valid
            @RequestBody SearchReviewRequestDto requestDto
    ) {
        Page<ReviewResponseDto> reviews = reviewService.getReviews(requestDto, page-1, size, sortBy, isAsc);
        return new ResponseEntity<>(ApiResponse.success(reviews), HttpStatus.OK);
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReview(@PathVariable("review_id") UUID reviewId) {
        Review review = reviewService.getReview(reviewId);
        ReviewResponseDto responseDto = new ReviewResponseDto(review);
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }

    @PatchMapping("/{review_id}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(
            @Valid
            @RequestBody UpdateReviewRequestDto requestDto,
            @PathVariable("review_id") UUID reviewId
            ) {
        Review review = reviewService.updateReview(reviewId, requestDto);
        ReviewResponseDto responseDto = new ReviewResponseDto(review);
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{review_id}")
    public void deleteReview(@PathVariable("review_id") UUID reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
