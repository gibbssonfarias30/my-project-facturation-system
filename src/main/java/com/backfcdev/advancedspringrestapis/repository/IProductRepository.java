package com.backfcdev.advancedspringrestapis.repository;

import com.backfcdev.advancedspringrestapis.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IProductRepository extends IGenericRepository<Product, Integer> {
    Page<Product> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
