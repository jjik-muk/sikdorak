package com.jjikmuk.sikdorak.store.service.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoAddressDocumentResponse {

	private KakaoAddressResponse address;
	private KakaoRoadAddressResponse roadAddress;
}
