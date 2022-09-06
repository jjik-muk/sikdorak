package com.jjikmuk.sikdorak.store.service.client;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPlaceSearchResponse {

	List<KakaoPlaceResponse> documents;
}
