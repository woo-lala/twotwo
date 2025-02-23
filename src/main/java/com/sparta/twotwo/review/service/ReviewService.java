package com.sparta.twotwo.review.service;

import com.sparta.twotwo.auth.util.SecurityUtil;
import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.entity.RolesEnum;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.order.repository.OrderRepository;
import com.sparta.twotwo.review.dto.CreateReviewRequestDto;
import com.sparta.twotwo.review.dto.ReviewResponseDto;
import com.sparta.twotwo.review.dto.SearchReviewRequestDto;
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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResponseDto createReview(CreateReviewRequestDto requestDto) {
        Member member = findMember(authenticateMember());
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new TwotwoApplicationException(ErrorCode.ORDER_NOT_FOUND));

        if (!Objects.equals(member.getMember_id(), order.getMember().getMember_id())) {
            throw new TwotwoApplicationException(ErrorCode.UNAUTHORIZED);
        }

        Review review = reviewRepository.save(new Review(requestDto, member, order));

        return new ReviewResponseDto(review);
    }

    public Page<ReviewResponseDto> getReviews(SearchReviewRequestDto requestDto, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = createPageable(page, size, isAsc, sortBy);
        Member member = findMember(authenticateMember());

        return reviewRepository.searchReviews(requestDto, pageable, hasManagerOrMasterRole(member.getRoles()));
    }

    public ReviewResponseDto getReview(UUID reviewId) {
        Member member = findMember(authenticateMember());
        Review review = findReview(reviewId);

        validateReviewAvailability(review);

        if (hasManagerOrMasterRole(member.getRoles())){
            return new ReviewResponseDto(review);
        }

        if (review.getIsHidden()) {
            validateMemberAuthentication(review);
        }

        return new ReviewResponseDto(review);
    }

    @Transactional
    public ReviewResponseDto updateReview(UUID reviewId, UpdateReviewRequestDto requestDto) {
        Review review = findReview(reviewId);
        validateMemberAuthentication(review);

        updateReviewFields(review, requestDto);
        review.update(review);

        return new ReviewResponseDto(review);
    }

    @Transactional
    public void deleteReview(UUID reviewId) {
        Review review = findReview(reviewId);
        Member member = findMember(authenticateMember());

        if (!hasManagerOrMasterRole(member.getRoles())) {
            validateMemberAuthentication(review);
        }

        review.setIsDeleted(true);
        review.setDeletedBy(authenticateMember());
        review.setDeletedAt(LocalDateTime.now());
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

    private Long authenticateMember() {
        return SecurityUtil.getMemberIdFromSecurityContext();
    }

    private void updateReviewFields(Review review, UpdateReviewRequestDto requestDto) {
        review.setRating(requestDto.getRating() != null ? requestDto.getRating() : review.getRating());
        review.setTitle(requestDto.getTitle() != null ? requestDto.getTitle() : review.getTitle());
        review.setContent(requestDto.getContent() != null ? requestDto.getContent() : review.getContent());
        review.setIsHidden(requestDto.isHidden());
    }

    private boolean hasManagerOrMasterRole(Set<String> roles) {
        return roles.contains(RolesEnum.MANAGER.toString()) || roles.contains(RolesEnum.MASTER.toString());
    }

    // 로그인된 사용자의 memberId가 해당 리뷰의 memberId와 일치하는지 확인
    private void validateMemberAuthentication(Review review) {
        if (!Objects.equals(authenticateMember(), review.getMember().getMember_id())) {
            throw new TwotwoApplicationException(ErrorCode.UNAUTHORIZED);
        }
    }

    private void validateReviewAvailability(Review review) {
        if (review.getIsDeleted()) {
            throw new TwotwoApplicationException(ErrorCode.REVIEW_NOT_FOUND);
        }
    }
}
