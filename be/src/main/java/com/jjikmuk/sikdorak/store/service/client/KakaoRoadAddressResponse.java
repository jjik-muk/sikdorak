package com.jjikmuk.sikdorak.store.service.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoRoadAddressResponse {

	private String addressName;

	@JsonProperty("region_1depth_name")
	private String region1DepthName;

	@JsonProperty("region_2depth_name")
	private String region2DepthName;

	@JsonProperty("region_3depth_name")
	private String region3DepthName;

	@JsonProperty("region_3depth_h_name")
	private String region3DepthHName;

	private String roadName;

	private String mainBuildingNo;

	private String subBuildingNo;
}
