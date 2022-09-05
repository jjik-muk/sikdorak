package com.jjikmuk.sikdorak.store.controller.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreVerifyAndUpsertResponse {

	@NotNull
	@Positive
	private Long storeId;

	@NotNull
	@Positive
	private Long kakaoPlaceId;

	@NotBlank
	private String storeName;

	@NotNull
	@Min(-180)
	@Min(180)
	private Double x;

	@NotNull
	@Min(-90)
	@Min(90)
	private Double y;

	public StoreVerifyAndUpsertResponse(Long storeId, Long kakaoPlaceId, String storeName, Double x,
		Double y) {
		this.storeId = storeId;
		this.kakaoPlaceId = kakaoPlaceId;
		this.storeName = storeName;
		this.x = x;
		this.y = y;
	}
}
