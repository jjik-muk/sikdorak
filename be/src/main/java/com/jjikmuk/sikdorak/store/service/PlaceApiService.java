package com.jjikmuk.sikdorak.store.service;

import com.jjikmuk.sikdorak.store.service.dto.AddressSearchRequest;
import com.jjikmuk.sikdorak.store.service.dto.AddressSearchResponse;
import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchRequest;
import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchResponse;

public interface PlaceApiService {

	PlaceSearchResponse searchPlaces(PlaceSearchRequest searchRequest);

	AddressSearchResponse searchAddress(AddressSearchRequest searchRequest);
}
