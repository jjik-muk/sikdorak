package com.jjikmuk.sikdorak.store.controller.response;

import com.jjikmuk.sikdorak.store.domain.Store;

public record StoreFindResponse(long id,
                                String storeName,
                                String contactNumber,
                                String baseAddress,
                                String detailAddress,
                                double latitude,
                                double longitude) {

    public static StoreFindResponse from(Store store) {
        return new StoreFindResponse(
                store.getId(),
                store.getStoreName(),
                store.getContactNumber(),
                store.getBaseAddress(),
                store.getDetailAddress(),
                store.getLatitude(),
                store.getLongitude()
        );
    }
}
