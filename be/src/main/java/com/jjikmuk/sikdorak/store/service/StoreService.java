package com.jjikmuk.sikdorak.store.service;

import com.jjikmuk.sikdorak.store.controller.request.StoreInsertRequest;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.StoreNotFoundException;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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

	public List<StoreSearchResponse> searchStoresByStoreNameContaining(String storeName) {
		if (storeName == null) {
			return Collections.emptyList();
		}

		return storeRepository.findStoresByStoreNameContaining(storeName)
			.stream()
			.map(StoreSearchResponse::from)
			.toList();
	}

	public Long insertStore(StoreInsertRequest insertRequest) {
		Store store = new Store(
			insertRequest.getStoreName(),
			insertRequest.getContactNumber(),
			insertRequest.getBaseAddress(),
			insertRequest.getDetailAddress(),
			insertRequest.getLatitude(),
			insertRequest.getLongitude()
		);

		return storeRepository.save(store)
			.getId();
	}
}
