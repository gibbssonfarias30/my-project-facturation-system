package com.backfcdev.advancedspringrestapis.service.impl;

import com.backfcdev.advancedspringrestapis.model.Category;
import com.backfcdev.advancedspringrestapis.repository.ICategoryRepository;
import com.backfcdev.advancedspringrestapis.repository.IGenericRepository;
import com.backfcdev.advancedspringrestapis.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl extends CRUDImpl<Category, Integer> implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    @Override
    protected IGenericRepository<Category, Integer> repository() {
        return categoryRepository;
    }
}
