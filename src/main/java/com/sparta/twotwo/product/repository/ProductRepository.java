package com.sparta.twotwo.product.repository;

import com.sparta.twotwo.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByIsHiddenFalse();

    List<Product> findByStoreId(UUID storeId);
}