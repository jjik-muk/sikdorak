package com.jjikmuk.sikdorak.store.service;

import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.StoreNotFoundException;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

	public Store findById(Long storeId) {
		if (Objects.isNull(storeId)) {
			throw new StoreNotFoundException();
		}
		return storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
	}

}
