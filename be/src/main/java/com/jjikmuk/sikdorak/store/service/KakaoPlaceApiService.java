package com.jjikmuk.sikdorak.store.service;

import com.jjikmuk.sikdorak.store.exception.InvalidXYException;
import com.jjikmuk.sikdorak.store.service.client.KakaoLocalKeywordSearchClient;
import com.jjikmuk.sikdorak.store.service.client.KakaoPlaceSearchResponse;
import com.jjikmuk.sikdorak.store.service.dto.PlaceResponse;
import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchRequest;
import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoPlaceApiService implements PlaceApiService {

	private static final int DEFAULT_RADIUS = 10;

	private final KakaoLocalKeywordSearchClient keywordSearchClient;

	@Override
	public PlaceSearchResponse searchPlaces(PlaceSearchRequest request) {
		validateXY(request.getX(), request.getY());

		KakaoPlaceSearchResponse placeSearchResponse = keywordSearchClient.searchPlaceWithKeyword(
			request.getStoreName(), request.getX(), request.getY(), DEFAULT_RADIUS);

		return new PlaceSearchResponse(convertToPlaceResponseList(placeSearchResponse));
	}

	private static void validateXY(Double x, Double y) {
		if ((x == null && y != null) || (x != null && y == null)) {
			throw new InvalidXYException();
		}
	}

	private static List<PlaceResponse> convertToPlaceResponseList(KakaoPlaceSearchResponse placeSearchResponse) {
		return placeSearchResponse.getDocuments()
			.stream()
			.map(place -> new PlaceResponse(
				place.getId(),
				place.getPlaceName(),
				place.getAddressName(),
				place.getRoadAddressName(),
				place.getPhone(),
				place.getX(),
				place.getY()
			))
			.collect(Collectors.toList());
	}
}
