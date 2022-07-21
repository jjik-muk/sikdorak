package com.jjikmuk.sikdorak.store.service;

import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public boolean existsById(Long storeId) {
        return storeRepository.existsById(storeId);
    }
}
