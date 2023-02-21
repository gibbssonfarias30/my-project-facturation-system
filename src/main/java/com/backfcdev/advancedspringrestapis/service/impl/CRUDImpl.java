package com.backfcdev.advancedspringrestapis.service.impl;

import com.backfcdev.advancedspringrestapis.exception.ModelNotFoundException;
import com.backfcdev.advancedspringrestapis.repository.IGenericRepository;
import com.backfcdev.advancedspringrestapis.service.ICRUD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepository<T, ID> repository();

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository().findAll(pageable);
    }

    @Override
    public List<T> findAll() {
        if(repository().findAll().isEmpty()){
            throw new ModelNotFoundException();
        }
        return repository().findAll();

    }

    @Override
    public T save(T t) {
        return repository().save(t);
    }

    @Override
    public T findById(ID id) {
        return repository().findById(id)
                .orElseThrow(() -> new ModelNotFoundException("ID Not Found " + id));
    }

    @Override
    public T update(ID id, T t) {
        repository().findById(id)
                .orElseThrow(() -> new ModelNotFoundException("ID Not Found " + id));
        return repository().save(t);

    }

    @Override
    public void delete(ID id) {
        repository().findById(id)
                .orElseThrow(() -> new ModelNotFoundException("ID Not Found " + id));
        repository().deleteById(id);
    }
}
