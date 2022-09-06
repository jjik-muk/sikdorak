package com.jjikmuk.sikdorak.store.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PlaceResponse(
	Long id,
	String placeName,
	String addressName,
	String roadAddressName,
	String contactNumber,
	Double x,
	Double y
) {

}
