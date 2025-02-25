package com.sparta.twotwo.store.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twotwo.store.dto.request.StoreSearchRequestDto;
import com.sparta.twotwo.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

import static com.sparta.twotwo.store.entity.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryQueryImpl implements StoreRepositoryQuery {

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Store> search(StoreSearchRequestDto search, Pageable pageable) {

        QueryResults<Store> results = queryFactory
                .selectFrom(store)
                .leftJoin(store.category).fetchJoin()
                .leftJoin(store.address).fetchJoin()
                .where(
                        isCategoryEqualTo(search.getCategoryName()),
                        containsStoreName(search.getStoreName())
                )
                .orderBy(createOrderSpecifier(search.getSortType()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression isCategoryEqualTo(String categoryName) {
        return Objects.nonNull(categoryName) ? store.category.name.eq(categoryName) : null;
    }

    private BooleanExpression containsStoreName(String storeName) {
        return Objects.nonNull(storeName) ? store.name.containsIgnoreCase(storeName) : null;
    }

    private OrderSpecifier createOrderSpecifier(StoreSearchRequestDto.SortType sortType) {
        return switch (sortType) {
            case CREATED_AT_DESC -> store.createdAt.desc();
            case UPDATED_AT_DESC -> store.updatedAt.desc();
        };
    }

}
