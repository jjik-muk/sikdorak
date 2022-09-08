package com.jjikmuk.sikdorak.store.service.dto;

import lombok.Getter;

@Getter
public class PlaceSearchRequest {

	private final String storeName;
	private final Double x;
	private final Double y;

	public PlaceSearchRequest(String storeName, Double x, Double y) {
		this.storeName = storeName;
		this.x = x;
		this.y = y;
	}
}
