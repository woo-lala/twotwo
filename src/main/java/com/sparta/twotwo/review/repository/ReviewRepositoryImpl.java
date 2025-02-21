package com.sparta.twotwo.review.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twotwo.review.dto.ReviewResponseDto;
import com.sparta.twotwo.review.dto.SearchReviewRequestDto;
import com.sparta.twotwo.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.twotwo.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewResponseDto> searchReviews(SearchReviewRequestDto searchDto, Pageable pageable, boolean hasAdminRole) {
        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        QueryResults<Review> results = queryFactory
                .selectFrom(review)
                .where(
                        hasAdminRole ? null : review.isHidden.isFalse(),
                        review.isDeleted.isFalse(),
                        titleContains(searchDto.getTitle()),
                        contentContains(searchDto.getContent()),
                        ratingEquals(searchDto.getRating()),
                        nicknameEquals(searchDto.getNickname())
                )
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ReviewResponseDto> content = results.getResults().stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate titleContains(String title) {
        return title != null ? review.title.containsIgnoreCase(title) : null;
    }

    private Predicate contentContains(String content) {
        return content != null ? review.content.containsIgnoreCase(content) : null;
    }

    private Predicate ratingEquals(Integer rating) {
        return rating != null ? review.rating.eq(rating) : null;
    }

    private Predicate nicknameEquals(String nickname) {
        return nickname != null ? review.member.nickname.eq(nickname) : null;
    }

    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort() != null) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
                switch (sortOrder.getProperty()) {
                    case "createdAt":
                        orders.add(new OrderSpecifier<>(direction, review.createdAt));
                        break;
                    case "updatedAt":
                        orders.add(new OrderSpecifier<>(direction, review.updatedAt));
                        break;
                    case "rating":
                        orders.add(new OrderSpecifier<>(direction, review.rating));
                        break;
                }
            }
        }
        return orders;
    }
}
