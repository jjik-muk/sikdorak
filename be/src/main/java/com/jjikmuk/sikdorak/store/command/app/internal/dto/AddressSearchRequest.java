package com.jjikmuk.sikdorak.store.command.app.internal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressSearchRequest {

	private String query;

	public AddressSearchRequest(String query) {
		this.query = query;
	}
}
