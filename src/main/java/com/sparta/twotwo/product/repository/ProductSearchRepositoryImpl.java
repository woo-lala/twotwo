package com.sparta.twotwo.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twotwo.product.dto.ProductListResponseDto;
import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.product.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements ProductSearchRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductListResponseDto> searchProducts(String keyword, Integer minPrice, Integer maxPrice, String sortBy, String sortDirection, Pageable pageable) {
        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(keyword)) {
            builder.and(product.productName.containsIgnoreCase(keyword));
        }

        if (minPrice != null) {
            builder.and(product.price.goe(minPrice));
        }
        if (maxPrice != null) {
            builder.and(product.price.loe(maxPrice));
        }

        builder.and(product.isDeleted.eq(false));

        var orderBy = sortBy.equals("updatedAt") ? product.updatedAt.desc() : product.createdAt.desc();
        if ("asc".equalsIgnoreCase(sortDirection)) {
            orderBy = sortBy.equals("updatedAt") ? product.updatedAt.asc() : product.createdAt.asc();
        }

        QueryResults<Product> results = queryFactory
                .selectFrom(product)
                .where(builder)
                .orderBy(orderBy)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(
                results.getResults().stream().map(prod -> ProductListResponseDto.builder()
                        .productId(prod.getId())
                        .productName(prod.getProductName())
                        .price(prod.getPrice())
                        .imageUrl(prod.getImageUrl())
                        .createdAt(prod.getCreatedAt().toString())
                        .updatedAt(prod.getUpdatedAt() != null ? prod.getUpdatedAt().toString() : null)
                        .build()).toList(),
                pageable,
                results.getTotal()
        );
    }
}
