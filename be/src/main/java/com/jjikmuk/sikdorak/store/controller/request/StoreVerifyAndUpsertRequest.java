package com.jjikmuk.sikdorak.store.controller.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreVerifyAndUpsertRequest {

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

	public StoreVerifyAndUpsertRequest(Long kakaoPlaceId, String storeName, Double x, Double y) {
		this.kakaoPlaceId = kakaoPlaceId;
		this.storeName = storeName;
		this.x = x;
		this.y = y;
	}
}
