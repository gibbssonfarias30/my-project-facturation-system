package com.backfcdev.advancedspringrestapis.service.impl;

import com.backfcdev.advancedspringrestapis.model.Order;
import com.backfcdev.advancedspringrestapis.repository.IGenericRepository;
import com.backfcdev.advancedspringrestapis.repository.IOrderRepository;
import com.backfcdev.advancedspringrestapis.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl extends CRUDImpl<Order, Integer> implements IOrderService {
    private final IOrderRepository orderRepository;

    @Override
    protected IGenericRepository<Order, Integer> repository() {
        return orderRepository;
    }
}
