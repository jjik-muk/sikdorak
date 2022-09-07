package com.jjikmuk.sikdorak.store.service.dto;

import lombok.Builder;

@Builder
public record AddressResponse(
	String addressName,         // 지번 주소
	String roadAddressName,     // 도로명 주소
	String region1DepthName,    // 지번.시도명
	String region2DepthName,    // 지번.시군구명
	String region3DepthName,    // 지번.법정읍면동명
	String region3DepthHName,   // 부가정보.행정동명
	String mainAddressNo,       // 지번.지번본번(번지)
	String subAddressNo,        // 지번.지번부번(호)
	String roadName,            // 지번.지번부번(호)
	String mainBuildingNo,      // 지번.지번부번(호)
	String subBuildingNo        // 지번.지번부번(호)
) {

	public AddressResponse(String addressName, String roadAddressName) {
		this(addressName, roadAddressName, null, null, null, null, null, null, null, null, null);
	}
}
