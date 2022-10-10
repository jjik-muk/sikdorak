package com.jjikmuk.sikdorak.store.command.app.internal.client;

import java.util.Collections;
import java.util.List;

public class KakaoAddressSearchResponse {

	List<KakaoAddressDocumentResponse> documents;

	public List<KakaoAddressDocumentResponse> getDocuments() {
		return Collections.unmodifiableList(documents);
	}
}
