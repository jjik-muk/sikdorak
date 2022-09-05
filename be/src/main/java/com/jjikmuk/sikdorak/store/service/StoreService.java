package com.jjikmuk.sikdorak.store.service;

import com.jjikmuk.sikdorak.store.controller.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.controller.request.UserLocationInfo;
import com.jjikmuk.sikdorak.store.controller.response.StoreRadiusSearchResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.domain.Address;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
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
            throw new NotFoundStoreException();
        }

        return storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
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

    public List<StoreRadiusSearchResponse> searchStoresByRadius(UserLocationInfo userLocationInfo) {

        List<StoreRadiusSearchResponse> result = new ArrayList<>();

        Stream.iterate(1, i -> i <= 10, i -> i + 1)
            .forEach(i -> result.add(new StoreRadiusSearchResponse(
                    i,
                    "맛있는가게" + i,
                    "02-0000-0000",
                    "서울시 송파구 송파동 35-1",
                    "서울시 송파구 좋은길 1",
                    127.105143,
                    37.509389
                ))
            );

        return result;
    }

    @Transactional
    public Long createStore(StoreCreateRequest createRequest) {
        Store store = new Store(
            createRequest.getStoreName(),
            createRequest.getContactNumber(),
            Address.of(createRequest.getAddressName(), createRequest.getRoadAddressName()),
            createRequest.getY(),
            createRequest.getX()
        );

        return storeRepository.save(store)
            .getId();
    }

    @Transactional
    public Long modifyStore(Long storeId, StoreModifyRequest modifyRequest) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(NotFoundStoreException::new);

        store.editAll(
            modifyRequest.getStoreName(),
            modifyRequest.getContactNumber(),
            Address.of(modifyRequest.getAddressName(), modifyRequest.getRoadAddressName()),
            modifyRequest.getY(),
            modifyRequest.getX()
        );

        return store.getId();
    }

    @Transactional
    public void removeStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(NotFoundStoreException::new);

        store.delete();
    }
}
