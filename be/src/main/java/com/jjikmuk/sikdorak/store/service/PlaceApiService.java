package com.jjikmuk.sikdorak.store.service;

import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchRequest;
import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchResponse;

public interface PlaceApiService {

	PlaceSearchResponse searchPlaces(PlaceSearchRequest searchRequest);
}
