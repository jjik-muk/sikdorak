package com.jjikmuk.sikdorak.store.command.app.internal;

import com.jjikmuk.sikdorak.store.command.app.internal.dto.AddressSearchRequest;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.AddressSearchResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.PlaceSearchRequest;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.PlaceSearchResponse;

public interface PlaceApiService {

	PlaceSearchResponse searchPlaces(PlaceSearchRequest searchRequest);

	AddressSearchResponse searchAddress(AddressSearchRequest searchRequest);
}
