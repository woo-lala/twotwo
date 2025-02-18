package com.sparta.twotwo.review.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.review.dto.CreateReviewRequestDto;
import com.sparta.twotwo.review.dto.ReviewResponseDto;
import com.sparta.twotwo.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(
            @RequestBody CreateReviewRequestDto requestDto
    ) {
        ReviewResponseDto responseDto = reviewService.createReview(requestDto);
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }
}
