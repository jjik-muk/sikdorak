package com.jjikmuk.sikdorak.store.service;

import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.store.controller.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreVerifyOrSaveRequest;
import com.jjikmuk.sikdorak.store.controller.request.UserLocationInfo;
import com.jjikmuk.sikdorak.store.controller.response.StoreDetailResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreRadiusSearchResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreVerifyOrSaveResponse;
import com.jjikmuk.sikdorak.store.domain.Address;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundApiAddressException;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.store.service.dto.AddressResponse;
import com.jjikmuk.sikdorak.store.service.dto.AddressSearchRequest;
import com.jjikmuk.sikdorak.store.service.dto.AddressSearchResponse;
import com.jjikmuk.sikdorak.store.service.dto.PlaceResponse;
import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchRequest;
import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

	private final StoreRepository storeRepository;

	private final ReviewRepository reviewRepository;
	private final PlaceApiService kakaoPlaceApiService;

	@Transactional(readOnly = true)
	public Store searchById(Long storeId) {
		if (Objects.isNull(storeId)) {
			throw new NotFoundStoreException();
		}

		return storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
	}

	@Transactional(readOnly = true)
	public List<StoreSearchResponse> searchStoresByStoreNameContaining(String storeName) {
		if (storeName == null) {
			return Collections.emptyList();
		}

		return storeRepository.findStoresByStoreNameContaining(storeName)
			.stream()
			.map(StoreSearchResponse::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<StoreRadiusSearchResponse> searchStoresByRadius(UserLocationInfo userLocationInfo) {

		List<StoreRadiusSearchResponse> result = new ArrayList<>();

		Stream.iterate(1, i -> i <= 10, i -> i + 1)
			.forEach(i -> result.add(new StoreRadiusSearchResponse(
					i,
					"맛있는가게" + i,
					"02-0000-0000",
					"서울시 송파구 송파동 35-1",
					"서울시 송파구 좋은길 1",
					127.105143,
					37.509389
				))
			);

		return result;
	}

	@Transactional
	public Long createStore(StoreCreateRequest createRequest) {
		Address address = Address.requiredFieldBuilder(
				createRequest.getAddressName(), createRequest.getRoadAddressName())
			.build();

		Store store = new Store(
			createRequest.getStoreName(),
			createRequest.getContactNumber(),
			address,
			createRequest.getX(),
			createRequest.getY()
		);

		return storeRepository.save(store)
			.getId();
	}

	@Transactional
	public Long modifyStore(Long storeId, StoreModifyRequest modifyRequest) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(NotFoundStoreException::new);

		Address address = Address.requiredFieldBuilder(
				modifyRequest.getAddressName(), modifyRequest.getRoadAddressName())
			.build();

		store.editAll(
			modifyRequest.getStoreName(),
			modifyRequest.getContactNumber(),
			address,
			modifyRequest.getY(),
			modifyRequest.getX()
		);

		return store.getId();
	}

	@Transactional
	public void removeStore(Long storeId) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(NotFoundStoreException::new);

		store.delete();
	}

	@Transactional
	public StoreVerifyOrSaveResponse verifyOrSave(StoreVerifyOrSaveRequest request) {
		Store store = storeRepository.findStoreByPlaceId(request.getPlaceId())
			.orElseGet(() -> searchApiAndSave(request));

		return StoreVerifyOrSaveResponse.from(store);
	}

	private Store searchApiAndSave(StoreVerifyOrSaveRequest request) {
		PlaceResponse placeResponse = searchPlaceApi(request);
		AddressResponse addressResponse = searchAddressApi(placeResponse);

		return storeRepository.save(new Store(
			placeResponse.id(),
			placeResponse.placeName(),
			placeResponse.contactNumber(),
			getAddress(addressResponse),
			placeResponse.x(),
			placeResponse.y()));
	}

	private PlaceResponse searchPlaceApi(StoreVerifyOrSaveRequest request) {
		PlaceSearchResponse placeSearchResponse = kakaoPlaceApiService.searchPlaces(
			new PlaceSearchRequest(
				request.getStoreName(),
				request.getX(),
				request.getY()
			));

		return findFirstPlace(request.getPlaceId(), placeSearchResponse);
	}

	private PlaceResponse findFirstPlace(Long placeId, PlaceSearchResponse placeSearchResponse) {
		return placeSearchResponse.getPlaces()
			.stream()
			.filter(place -> place.id().equals(placeId))
			.findFirst()
			.orElseThrow(NotFoundStoreException::new);
	}

	private AddressResponse searchAddressApi(PlaceResponse place) {
		AddressSearchResponse addresses = kakaoPlaceApiService.searchAddress(
			new AddressSearchRequest(place.addressName()));

		try {
			return addresses.getAddressResponses()
				.stream()
				.findFirst()
				.orElseThrow(NotFoundApiAddressException::new);
		} catch (Exception e) {
			// 예측하지 못한 예외도 해당 로직을 타게 하기 Exception 으로 선언
			log.error(e.getMessage(), e);
			return new AddressResponse(place.addressName(), place.roadAddressName());
		}
	}

	private Address getAddress(AddressResponse addressResponse) {
		return Address.requiredFieldBuilder(
				addressResponse.addressName(),
				addressResponse.roadAddressName())
			.region1DepthName(addressResponse.region1DepthName())
			.region2DepthName(addressResponse.region2DepthName())
			.region3DepthName(addressResponse.region3DepthName())
			.region3DepthHName(addressResponse.region3DepthHName())
			.mainAddressNo(addressResponse.mainAddressNo())
			.subAddressNo(addressResponse.subAddressNo())
			.roadName(addressResponse.roadName())
			.mainBuildingNo(addressResponse.mainBuildingNo())
			.subBuildingNo(addressResponse.subBuildingNo())
			.build();
	}

	@Transactional(readOnly = true)
	public StoreDetailResponse searchDetail(Long storeId) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(NotFoundStoreException::new);

		return new StoreDetailResponse(
			storeId,
			store.getStoreName(),
			store.getAddressName(),
			store.getRoadAddressName(),
			store.getContactNumber(),
			store.getX(),
			store.getY(),
			reviewRepository.countByStoreId(storeId),
			reviewRepository.findReviewScoreAverageByStoreId(storeId)
				.orElse(0.0)
		);
	}
}
