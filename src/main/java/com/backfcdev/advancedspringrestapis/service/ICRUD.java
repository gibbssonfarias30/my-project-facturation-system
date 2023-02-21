package com.backfcdev.advancedspringrestapis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICRUD<T, ID>{
    Page<T> findAll(Pageable pageable);
    List<T> findAll();
    T save(T t);
    T findById(ID id);
    T update(ID id, T t);
    void delete(ID id);


}
