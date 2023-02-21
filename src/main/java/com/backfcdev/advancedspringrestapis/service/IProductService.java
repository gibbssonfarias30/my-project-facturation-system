package com.backfcdev.advancedspringrestapis.service;

import com.backfcdev.advancedspringrestapis.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IProductService extends ICRUD<Product, Integer> {
    Page<Product> findByName(String name, Pageable pageable);
    Page<Product> findByArgs(final Optional<String> name, final Optional<Double> price, Pageable pageable);
}
