package com.backfcdev.advancedspringrestapis.service.impl;

import com.backfcdev.advancedspringrestapis.model.Product;
import com.backfcdev.advancedspringrestapis.repository.IGenericRepository;
import com.backfcdev.advancedspringrestapis.repository.IProductRepository;
import com.backfcdev.advancedspringrestapis.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    private final IProductRepository productRepository;

    @Override
    protected IGenericRepository<Product, Integer> repository() {
        return productRepository;
    }

    @Override
    public Page<Product> findByName(String name, Pageable pageable) {
        return productRepository.findByNameContainsIgnoreCase(name, pageable);
    }

    @Override
    public Page<Product> findByArgs(Optional<String> name, Optional<Double> price, Pageable pageable) {
        Specification<Product> searchProductName = (root, query, criteriaBuilder) -> {
            if(name.isPresent()){
                // search products by name
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.get() + "%");
            }
            // that is, we do not filter anything
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };

        Specification<Product> priceLessThan = (root, query, criteriaBuilder) -> {
            // search product with the price less than or equal to
            if(price.isPresent()){
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), price.get());
            }
            // that is, we do not filter anything
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };

        Specification<Product> bothSpecifications = searchProductName.and(priceLessThan);

        return productRepository.findAll(bothSpecifications, pageable);
    }

}
