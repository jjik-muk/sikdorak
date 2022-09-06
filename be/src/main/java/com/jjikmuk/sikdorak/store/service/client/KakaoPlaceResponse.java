package com.jjikmuk.sikdorak.store.service.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoPlaceResponse(
	Long id,
	String placeName,
	String addressName,
	String roadAddressName,
	String phone,
	Double x,
	Double y
) {

}
