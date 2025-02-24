package com.sparta.twotwo.store.repository;

import com.sparta.twotwo.store.dto.StoreSearchRequestDto;
import com.sparta.twotwo.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryQuery {

    Page<Store> search(StoreSearchRequestDto search, Pageable pageable);


}
