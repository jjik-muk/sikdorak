package com.jjikmuk.sikdorak.review.controller.response.reviewdetail;

import com.jjikmuk.sikdorak.store.domain.Store;

/**
 * - [ ] TODO : Store 대표 이미지 추가
 */
public record ReviewDetailStoreResponse(
	long storeId,
	String storeName,
	String storeAddress
) {

	public ReviewDetailStoreResponse(Store store) {
		this(store.getId(),
			store.getStoreName(),
			store.getBaseAddress());
	}
}

