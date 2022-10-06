package com.jjikmuk.sikdorak.store.command.app.internal.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoPlaceResponse {

	private Long id;
	private String placeName;
	private String addressName;
	private String roadAddressName;
	private String phone;
	private Double x;
	private Double y;
}
