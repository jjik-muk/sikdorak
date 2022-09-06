package com.jjikmuk.sikdorak.store.service.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoAddressResponse {

	private String addressName;
	private String region1DepthName;
	private String region2DepthName;
	private String region3DepthName;
	private String region3DepthHName;
	private String mainAddressNo;
	private String subAddressNo;
}
