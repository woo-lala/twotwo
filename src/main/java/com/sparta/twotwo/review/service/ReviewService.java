package com.sparta.twotwo.review.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.repository.OrderRepository;
import com.sparta.twotwo.review.dto.CreateReviewRequestDto;
import com.sparta.twotwo.review.dto.ReviewResponseDto;
import com.sparta.twotwo.review.dto.UpdateReviewRequestDto;
import com.sparta.twotwo.review.entity.Review;
import com.sparta.twotwo.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResponseDto createReview(CreateReviewRequestDto requestDto) {
        Member member = findMember(requestDto.getMemberId());
        // 사용자의 주문인지 확인하는 로직 구현 필요
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));

        Review review = reviewRepository.save(new Review(requestDto, member, order));

        return new ReviewResponseDto(review);
    }

    public Page<ReviewResponseDto> getReviews(int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = createPageable(page, size, isAsc, sortBy);

        Page<Review> reviewList = reviewRepository.findByIsHiddenFalseAndIsDeletedFalse(pageable);

        return reviewList.map(ReviewResponseDto::new);
    }

    public ReviewResponseDto getReview(UUID reviewId) {
        // MANAGER, MASTER 를 제외한 권한은
        // 로그인된 사용자의 memberId와 Review의 memberId가 일치하는지 확인 필요
        Review review = findReview(reviewId);

        return new ReviewResponseDto(review);
    }

    @Transactional
    public ReviewResponseDto updateReview(UUID reviewId, UpdateReviewRequestDto requestDto) {
        // 로그인된 사용자의 memberId와 Review의 memberId가 일치하는지 확인 필요
        Review review = findReview(reviewId);

        review.setRating(requestDto.getRating() != null ? requestDto.getRating() : review.getRating());
        review.setTitle(requestDto.getTitle() != null ? requestDto.getTitle() : review.getTitle());
        review.setContent(requestDto.getContent() != null ? requestDto.getContent() : review.getContent());

        review.update(review);

        return new ReviewResponseDto(review);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Review findReview(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.REVIEW_NOT_FOUND));
    }

    private Pageable createPageable(int page, int size, boolean isAsc, String sortBy) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        return PageRequest.of(page, size, sort);
    }

}
