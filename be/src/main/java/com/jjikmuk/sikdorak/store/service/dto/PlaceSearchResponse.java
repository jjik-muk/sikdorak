package com.jjikmuk.sikdorak.store.service.dto;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PlaceSearchResponse {

	List<PlaceResponse> places;

	public List<PlaceResponse> getPlaces() {
		return Collections.unmodifiableList(places);
	}
}
