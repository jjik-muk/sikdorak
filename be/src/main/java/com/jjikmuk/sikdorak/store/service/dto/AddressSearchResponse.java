package com.jjikmuk.sikdorak.store.service.dto;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AddressSearchResponse {

	private List<AddressResponse> addressResponses;

	public List<AddressResponse> getAddressResponses() {
		return Collections.unmodifiableList(addressResponses);
	}
}
