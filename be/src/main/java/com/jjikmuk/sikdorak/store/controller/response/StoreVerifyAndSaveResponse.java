package com.jjikmuk.sikdorak.store.controller.response;

import com.jjikmuk.sikdorak.store.domain.Store;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record StoreVerifyAndSaveResponse (

	@NotNull
	@Positive
	Long storeId,

	@NotNull
	@Positive
	Long placeId,

	@NotBlank
	String storeName,

	@NotNull
	@Min(-180)
	@Min(180)
	Double x,

	@NotNull
	@Min(-90)
	@Min(90)
	Double y
) {
	
	public static StoreVerifyAndSaveResponse from(Store store) {
		return new StoreVerifyAndSaveResponse(
			store.getId(),
			store.getPlaceId(),
			store.getStoreName(),
			store.getX(),
			store.getY()
		);
	}
}
