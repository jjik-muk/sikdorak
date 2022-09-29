package com.jjikmuk.sikdorak.store.service.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record StoreDetailResponse(

	@NotNull
	long storeId,

	@NotBlank
	String storeName,

	@NotBlank
	String addressName,

	@NotBlank
	String roadAddressName,

	String contactNumber,

	@NotNull
	@Min(-180)
	@Min(180)
	double x,

	@NotNull
	@Min(-90)
	@Min(90)
	double y,

	@NotNull
	@Min(0)
	@Min(Integer.MAX_VALUE)
	int reviewCounts,

	@NotNull
	@Min(1)
	@Min(5)
	double reviewScoreAverage
) {

}
