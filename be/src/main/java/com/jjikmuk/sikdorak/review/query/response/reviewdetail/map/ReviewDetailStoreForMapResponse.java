package com.jjikmuk.sikdorak.review.query.response.reviewdetail.map;

import com.jjikmuk.sikdorak.store.command.domain.Store;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
- [ ] TODO : Store 대표 이미지 추가.
 */
public record ReviewDetailStoreForMapResponse(
	@NotNull
	long storeId,

	@NotBlank
	@Size(min = 1, max = 500)
	String storeName,

	String addressName,
	String roadAddressName,
	double x,
	double y

	// String storeImage
) {

	public ReviewDetailStoreForMapResponse(Store store) {
		this(store.getId(),
			store.getStoreName(),
			store.getAddressName(),
			store.getRoadAddressName(),
			store.getX(),
			store.getY()
		);
	}
}

