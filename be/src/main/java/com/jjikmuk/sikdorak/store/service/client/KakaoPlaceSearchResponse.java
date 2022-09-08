package com.jjikmuk.sikdorak.store.service.client;

import java.util.Collections;
import java.util.List;

public class KakaoPlaceSearchResponse {

	List<KakaoPlaceResponse> documents;

	public List<KakaoPlaceResponse> getDocuments() {
		return Collections.unmodifiableList(documents);
	}
}
