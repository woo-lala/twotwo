package com.sparta.twotwo.review.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.repository.OrderRepository;
import com.sparta.twotwo.review.dto.CreateReviewRequestDto;
import com.sparta.twotwo.review.dto.ReviewResponseDto;
import com.sparta.twotwo.review.entity.Review;
import com.sparta.twotwo.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResponseDto createReview(CreateReviewRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.MEMBER_NOT_FOUND));
        // 사용자의 주문인지 확인하는 로직 구현 필요
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));

        Review review = reviewRepository.save(new Review(requestDto, member, order));

        return new ReviewResponseDto(review);
    }
}
