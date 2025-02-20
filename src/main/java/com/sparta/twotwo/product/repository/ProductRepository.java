package com.sparta.twotwo.product.repository;

import com.sparta.twotwo.product.entity.Product;
import com.sparta.twotwo.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByStore(Store store);
}