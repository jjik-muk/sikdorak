package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.command.domain.Address;
import com.jjikmuk.sikdorak.store.command.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.command.domain.StoreRepository;
import com.jjikmuk.sikdorak.store.command.app.internal.PlaceApiService;
import com.jjikmuk.sikdorak.store.command.app.StoreService;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.AddressResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.AddressSearchResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.PlaceResponse;
import com.jjikmuk.sikdorak.store.command.app.internal.dto.PlaceSearchResponse;
import com.jjikmuk.sikdorak.store.command.app.request.StoreVerifyOrSaveRequest;
import com.jjikmuk.sikdorak.store.command.app.response.StoreVerifyOrSaveResponse;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("통합 : Store 검증 및 저장 (카카오 API 사용)")
class StoreVerifyOrSaveIntegrationTest extends InitIntegrationTest {

	@Autowired
	private StoreService storeService;

	@MockBean
	private PlaceApiService placeApiService;

	@Autowired
	private StoreRepository storeRepository;

	@BeforeEach
	void setUp() {
		when(placeApiService.searchPlaces(any()))
			.thenReturn(getPlaceSearchResponse());
		when(placeApiService.searchAddress(any()))
			.thenReturn(getAddressSearchResponse());
	}

	private PlaceSearchResponse getPlaceSearchResponse() {

		return new PlaceSearchResponse(
			List.of(
				new PlaceResponse(
					1455921244L,
					"한국계",
					"서울 송파구 잠실동 177-5",
					"서울 송파구 올림픽로8길 10",
					"02-424-7077",
					127.079996336912,
					37.5107289013413
				)
			)
		);
	}

	@Nested
	@DisplayName("가게 등록을 검증할 때")
	class VerifyOrSaveStoreTest {

		@Nested
		@DisplayName("저장되어있는 가게라면")
		class SavedStore {

			@Test
			@DisplayName("조회된 가게 정보를 반환한다.")
			void exactly_same_store_name_returns_store_list() {
				// given
				Store savedStore = testData.store;
				Long placeId = savedStore.getPlaceId();
				String storeName = savedStore.getStoreName();

				// when
				StoreVerifyOrSaveResponse response = storeService.verifyOrSave(
					new StoreVerifyOrSaveRequest(placeId, storeName, null, null));

				// then
				assertThat(response).isNotNull();
				assertThat(response.storeId()).isEqualTo(savedStore.getId());
				assertThat(response.storeName()).isEqualTo(savedStore.getStoreName());
			}
		}

		@Nested
		@DisplayName("저장되어 있지 않고 장소 API 조회가 성공한다면")
		class SearchPlaceSuccess {

			@Test
			@DisplayName("조회된 가게 정보를 저장하고 반환한다.")
			void search_store_from_api_and_returns_store_list() {
				// given
				StoreVerifyOrSaveRequest request = new StoreVerifyOrSaveRequest(1455921244L, "한국계",
					127.111, 37.111);

				// when
				StoreVerifyOrSaveResponse response = storeService.verifyOrSave(
					request);

				// then
				assertThat(response).isNotNull();
				assertThat(response.placeId()).isEqualTo(request.getPlaceId());
				assertThat(response.storeName()).isEqualTo(request.getStoreName());

				Store savedStore = storeRepository.findById(response.storeId())
					.orElseThrow();

				assertThat(savedStore).isNotNull();
				assertThat(savedStore.getPlaceId()).isEqualTo(request.getPlaceId());
				assertThat(savedStore.getStoreName()).contains(request.getStoreName());
			}
		}

		@Nested
		@DisplayName("저장되어 있지 않고 장소 API 조회가 실패한다면")
		class SearchPlaceFailed {

			@Test
			@DisplayName("장소 API 에서 조회되지 않으면 예외를 발생시킨다.")
			void null_store_name_returns_empty_list() {
				// given
				long notExistingPlaceId = Long.MIN_VALUE;
				StoreVerifyOrSaveRequest request = new StoreVerifyOrSaveRequest(
					notExistingPlaceId, "없는가게이름입니다", 127.111, 37.111);

				// then
				assertThatThrownBy(() -> storeService.verifyOrSave(request))
					.isInstanceOf(NotFoundStoreException.class);
			}
		}

		@Nested
		@DisplayName("저장되어 있지 않고 장소 API 와 주소 API 조회가 성공한다면")
		class SearchPlaceAndAddressSuccess {

			@Test
			@DisplayName("조회된 가게 정보와 상세 주소를 저장하고 반환한다.")
			void search_store_from_api_and_returns_store_list() {
				// given
				String expectedAddressName = "서울 송파구 잠실동 177-5";
				String expectedRoadAddressName = "서울 송파구 올림픽로8길 10";
				String expectedRegion1DepthName = "서울";
				String expectedRegion2DepthName = "송파구";
				String expectedRegion3DepthName = "잠실본동";
				String expectedRegion3DepthHName = "잠실동";
				StoreVerifyOrSaveRequest request = new StoreVerifyOrSaveRequest(1455921244L, "한국계",
					127.111, 37.111);

				// when
				StoreVerifyOrSaveResponse response = storeService.verifyOrSave(
					request);

				// then
				assertThat(response).isNotNull();
				assertThat(response.placeId()).isEqualTo(request.getPlaceId());
				assertThat(response.storeName()).isEqualTo(request.getStoreName());

				Store savedStore = storeRepository.findById(response.storeId())
					.orElseThrow();

				assertThat(savedStore).isNotNull();
				assertThat(savedStore.getPlaceId()).isEqualTo(request.getPlaceId());
				assertThat(savedStore.getStoreName()).contains(request.getStoreName());

				Address address = savedStore.getAddress();
				assertAll(
					() -> assertThat(address).isNotNull(),
					() -> assertThat(address.getAddressName()).isEqualTo(expectedAddressName),
					() -> assertThat(address.getRoadAddressName()).isEqualTo(
						expectedRoadAddressName),
					() -> assertThat(address.getRegion1DepthName()).isEqualTo(
						expectedRegion1DepthName),
					() -> assertThat(address.getRegion2DepthName()).isEqualTo(
						expectedRegion2DepthName),
					() -> assertThat(address.getRegion3DepthName()).isEqualTo(
						expectedRegion3DepthName),
					() -> assertThat(address.getRegion3DepthHName()).isEqualTo(
						expectedRegion3DepthHName)
				);
			}
		}

		@Nested
		@DisplayName("저장되어 있지 않고 장소 API 는 성공하고 주소 API 는 실패한다면")
		class SearchPlaceSuccessAndAddressFailed {

			@Test
			@DisplayName("상세 주소 없이 조회된 가게 정보만 저장하고 반환한다.")
			void search_store_from_api_and_returns_store_list() {
				// given
				when(placeApiService.searchAddress(any()))
					.thenReturn(new AddressSearchResponse(Collections.emptyList()));

				String expectedAddressName = "서울 송파구 잠실동 177-5";
				String expectedRoadAddressName = "서울 송파구 올림픽로8길 10";
				StoreVerifyOrSaveRequest request = new StoreVerifyOrSaveRequest(1455921244L, "한국계",
					127.111, 37.111);

				// when
				StoreVerifyOrSaveResponse response = storeService.verifyOrSave(
					request);

				// then
				assertThat(response).isNotNull()
					.satisfies(res -> {
						assertThat(res.placeId()).isEqualTo(request.getPlaceId());
						assertThat(res.storeName()).isEqualTo(request.getStoreName());
					});

				Store savedStore = storeRepository.findById(response.storeId())
					.orElseThrow();

				assertThat(savedStore).isNotNull();
				assertThat(savedStore.getPlaceId()).isEqualTo(request.getPlaceId());
				assertThat(savedStore.getStoreName()).contains(request.getStoreName());

				Address address = savedStore.getAddress();
				assertAll(
					() -> assertThat(address).isNotNull(),
					() -> assertThat(address.getAddressName()).isEqualTo(expectedAddressName),
					() -> assertThat(address.getRoadAddressName()).isEqualTo(
						expectedRoadAddressName),
					() -> assertThat(address.getRegion1DepthName()).isNull(),
					() -> assertThat(address.getRegion2DepthName()).isNull(),
					() -> assertThat(address.getRegion3DepthName()).isNull(),
					() -> assertThat(address.getRegion3DepthHName()).isNull()
				);
			}
		}
	}

	private AddressSearchResponse getAddressSearchResponse() {
		return new AddressSearchResponse(
			List.of(
				AddressResponse.builder()
					.addressName("서울 송파구 잠실동 177-5")
					.roadAddressName("서울 송파구 올림픽로8길 10")
					.region1DepthName("서울")
					.region2DepthName("송파구")
					.region3DepthName("잠실본동")
					.region3DepthHName("잠실동")
					.mainAddressNo("177")
					.subAddressNo("5")
					.roadName("올림픽대로8길")
					.mainBuildingNo("10")
					.subBuildingNo("")
					.build()
			)
		);
	}
}
