package com.jjikmuk.sikdorak.store.command.app.internal;

import com.jjikmuk.sikdorak.store.command.app.internal.client.KakaoAddressResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.client.KakaoAddressSearchResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.client.KakaoLocalAddressSearchClient;
import com.jjikmuk.sikdorak.store.command.app.internal.client.KakaoLocalKeywordSearchClient;
import com.jjikmuk.sikdorak.store.command.app.internal.client.KakaoPlaceSearchResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.client.KakaoRoadAddressResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.AddressResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.AddressSearchRequest;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.AddressSearchResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.PlaceResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.PlaceSearchRequest;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.PlaceSearchResponse;
import com.jjikmuk.sikdorak.store.exception.InvalidAddressException;
import com.jjikmuk.sikdorak.store.exception.InvalidXYException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoPlaceApiService implements PlaceApiService {

	private static final int DEFAULT_RADIUS = 10;

	private final KakaoLocalKeywordSearchClient keywordSearchClient;
	private final KakaoLocalAddressSearchClient addressSearchClient;

	@Override
	public PlaceSearchResponse searchPlaces(PlaceSearchRequest request) {
		validateXY(request.getX(), request.getY());

		KakaoPlaceSearchResponse placeSearchResponse = keywordSearchClient.searchPlaceWithKeyword(
			request.getStoreName(), request.getX(), request.getY(), DEFAULT_RADIUS);

		return new PlaceSearchResponse(convertToPlaceResponseList(placeSearchResponse));
	}

	private void validateXY(Double x, Double y) {
		if ((x == null && y != null) || (x != null && y == null)) {
			throw new InvalidXYException();
		}
	}

	private List<PlaceResponse> convertToPlaceResponseList(
		KakaoPlaceSearchResponse placeSearchResponse) {
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
			.toList();
	}

	@Override
	public AddressSearchResponse searchAddress(AddressSearchRequest searchRequest) {
		String addressName = searchRequest.getQuery();
		validateAddressName(addressName);

		KakaoAddressSearchResponse addressResponse = addressSearchClient.searchAddress(addressName);

		return new AddressSearchResponse(convertToAddressResponseList(addressResponse));
	}

	private void validateAddressName(String addressName) {
		if (addressName == null) {
			throw new InvalidAddressException();
		}
	}

	private List<AddressResponse> convertToAddressResponseList(
		KakaoAddressSearchResponse addressResponse) {
		return addressResponse.getDocuments()
			.stream()
			.map(document -> getAddressResponse(document.getAddress(), document.getRoadAddress()))
			.toList();
	}

	private AddressResponse getAddressResponse(KakaoAddressResponse address,
		KakaoRoadAddressResponse roadAddress) {
		return AddressResponse.builder()
			.addressName(address.getAddressName())
			.roadAddressName(roadAddress.getAddressName())
			.region1DepthName(address.getRegion1DepthName())
			.region2DepthName(address.getRegion2DepthName())
			.region3DepthName(address.getRegion3DepthName())
			.region3DepthHName(address.getRegion3DepthHName())
			.mainAddressNo(address.getMainAddressNo())
			.subAddressNo(address.getSubAddressNo())
			.roadName(roadAddress.getRoadName())
			.mainBuildingNo(roadAddress.getMainBuildingNo())
			.subBuildingNo(roadAddress.getSubBuildingNo())
			.build();
	}
}
