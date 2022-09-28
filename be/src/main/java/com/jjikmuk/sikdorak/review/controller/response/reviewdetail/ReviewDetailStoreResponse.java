package com.jjikmuk.sikdorak.review.controller.response.reviewdetail;

import com.jjikmuk.sikdorak.store.domain.Store;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
- [ ] TODO : Store 대표 이미지 추가.
 */
public record ReviewDetailStoreResponse(
	@NotNull
	long storeId,

	@NotBlank
	@Size(min = 1, max = 500)
	String storeName,

	String addressName,
	String roadAddressName

	// String storeImage
) {

	public ReviewDetailStoreResponse(Store store) {
		this(store.getId(),
			store.getStoreName(),
			store.getAddressName(),
			store.getRoadAddressName()
		);
	}
}

