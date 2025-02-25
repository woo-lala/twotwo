package com.sparta.twotwo.product.repository;

import com.sparta.twotwo.product.dto.ProductListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearchRepository {
    Page<ProductListResponseDto> searchProducts(String keyword, Integer minPrice, Integer maxPrice, String sortBy, String sortDirection, Pageable pageable);
}


