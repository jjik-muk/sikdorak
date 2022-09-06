package com.jjikmuk.sikdorak.store.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceSearchRequest {

	private String storeName;
	private Double x;
	private Double y;
}
