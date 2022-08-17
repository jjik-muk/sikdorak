package com.jjikmuk.sikdorak.store.service;

import com.jjikmuk.sikdorak.store.controller.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.StoreNotFoundException;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;

	@Transactional(readOnly = true)
	public Store searchById(Long storeId) {
		if (Objects.isNull(storeId)) {
			throw new StoreNotFoundException();
		}

		return storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public List<StoreSearchResponse> searchStoresByStoreNameContaining(String storeName) {
		if (storeName == null) {
			return Collections.emptyList();
		}

		return storeRepository.findStoresByStoreNameContaining(storeName)
			.stream()
			.map(StoreSearchResponse::from)
			.toList();
	}

	@Transactional
	public Long createStore(StoreCreateRequest createRequest) {
		Store store = new Store(
			createRequest.getStoreName(),
			createRequest.getContactNumber(),
			createRequest.getBaseAddress(),
			createRequest.getDetailAddress(),
			createRequest.getLatitude(),
			createRequest.getLongitude()
		);

		return storeRepository.save(store)
			.getId();
	}

	@Transactional
	public Long modifyStore(Long storeId, StoreModifyRequest modifyRequest) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(StoreNotFoundException::new);

		store.editAll(
			modifyRequest.getStoreName(),
			modifyRequest.getContactNumber(),
			modifyRequest.getBaseAddress(),
			modifyRequest.getDetailAddress(),
			modifyRequest.getLatitude(),
			modifyRequest.getLongitude()
		);

		return store.getId();
	}
}
