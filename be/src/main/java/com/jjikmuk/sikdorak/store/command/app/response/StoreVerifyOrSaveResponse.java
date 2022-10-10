package com.jjikmuk.sikdorak.store.command.app.response;

import com.jjikmuk.sikdorak.store.command.domain.Store;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record StoreVerifyOrSaveResponse(

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
	
	public static StoreVerifyOrSaveResponse from(Store store) {
		return new StoreVerifyOrSaveResponse(
			store.getId(),
			store.getPlaceId(),
			store.getStoreName(),
			store.getX(),
			store.getY()
		);
	}
}
