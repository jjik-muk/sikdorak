package com.jjikmuk.sikdorak.store.command.app.internal.client;

import java.util.Collections;
import java.util.List;

public class KakaoPlaceSearchResponse {

	List<KakaoPlaceResponse> documents;

	public List<KakaoPlaceResponse> getDocuments() {
		return Collections.unmodifiableList(documents);
	}
}
