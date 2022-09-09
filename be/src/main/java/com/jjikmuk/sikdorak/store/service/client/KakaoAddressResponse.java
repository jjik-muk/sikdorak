package com.jjikmuk.sikdorak.store.service.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoAddressResponse {

	private String addressName;

	@JsonProperty("region_1depth_name")
	private String region1DepthName;

	@JsonProperty("region_2depth_name")
	private String region2DepthName;

	@JsonProperty("region_3depth_name")
	private String region3DepthName;

	@JsonProperty("region_3depth_h_name")
	private String region3DepthHName;

	private String mainAddressNo;

	private String subAddressNo;
}
