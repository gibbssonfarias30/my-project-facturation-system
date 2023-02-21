package com.backfcdev.advancedspringrestapis.service.impl;

import com.backfcdev.advancedspringrestapis.model.Client;
import com.backfcdev.advancedspringrestapis.repository.IClientRepository;
import com.backfcdev.advancedspringrestapis.repository.IGenericRepository;
import com.backfcdev.advancedspringrestapis.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl extends CRUDImpl<Client, Integer> implements IClientService {
    private final IClientRepository clientRepository;

    @Override
    protected IGenericRepository<Client, Integer> repository() {
        return clientRepository;
    }
}
