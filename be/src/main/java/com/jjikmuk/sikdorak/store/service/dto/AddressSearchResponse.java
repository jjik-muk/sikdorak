package com.jjikmuk.sikdorak.store.service.dto;

import java.util.Collections;
import java.util.List;

public class AddressSearchResponse {

	private final List<AddressResponse> addressResponses;

	public AddressSearchResponse(List<AddressResponse> addressResponses) {
		this.addressResponses = addressResponses;
	}

	public List<AddressResponse> getAddressResponses() {
		return Collections.unmodifiableList(addressResponses);
	}
}
