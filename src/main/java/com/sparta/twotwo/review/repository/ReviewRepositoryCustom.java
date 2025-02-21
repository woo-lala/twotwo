package com.sparta.twotwo.review.repository;

import com.sparta.twotwo.review.dto.ReviewResponseDto;
import com.sparta.twotwo.review.dto.SearchReviewRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewResponseDto> searchReviews(SearchReviewRequestDto searchDto, Pageable pageable, boolean hasAdminRole);
}
