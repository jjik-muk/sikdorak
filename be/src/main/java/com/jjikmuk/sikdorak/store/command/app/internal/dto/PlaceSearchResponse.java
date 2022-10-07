package com.jjikmuk.sikdorak.store.command.app.internal.dto;

import java.util.Collections;
import java.util.List;

public class PlaceSearchResponse {

	List<PlaceResponse> places;

	public PlaceSearchResponse(List<PlaceResponse> places) {
		this.places = places;
	}

	public List<PlaceResponse> getPlaces() {
		return Collections.unmodifiableList(places);
	}
}
