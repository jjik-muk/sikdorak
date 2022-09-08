package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.mock.WireMockPlaceApiTest;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.exception.InvalidXYException;
import com.jjikmuk.sikdorak.store.service.PlaceApiService;
import com.jjikmuk.sikdorak.store.service.dto.AddressSearchRequest;
import com.jjikmuk.sikdorak.store.service.dto.AddressSearchResponse;
import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchRequest;
import com.jjikmuk.sikdorak.store.service.dto.PlaceSearchResponse;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;


@WireMockPlaceApiTest
@DisplayName("PlaceApi 통합테스트")
public class PlaceApiIntegrationTest extends InitIntegrationTest {

	@Autowired
	private PlaceApiService kakaoPlaceApiService;

	@Nested
	@DisplayName("장소를 검색할 때")
	class SearchKeywordTest {

		@Test
		@DisplayName("입력값이 정상이면 장소 목록이 조회된다")
		void search_place_success() {
		    // given
			String storeName = "한국계";
			double x = 127.10796497353;
			double y = 37.5145458257413;
			PlaceSearchRequest searchRequest = new PlaceSearchRequest(storeName, x, y);

			// when
			PlaceSearchResponse searchResponse = kakaoPlaceApiService.searchPlaces(searchRequest);

		    // then
			assertThat(searchResponse.getPlaces()).isNotEmpty()
				.allSatisfy(place -> assertThat(place.placeName()).contains(storeName));
		}

		@Test
		@DisplayName("x, y 둘다 입력하지 않아도 입력하지 않아도 조회가 성공한다")
		void search_place_success_without_x_y() {
			// given
			String storeName = "한국계";
			PlaceSearchRequest searchRequest = new PlaceSearchRequest(storeName, null, null);

			// when
			PlaceSearchResponse searchResponse = kakaoPlaceApiService.searchPlaces(searchRequest);

			// then
			assertThat(searchResponse.getPlaces()).isNotEmpty()
				.allSatisfy(place -> assertThat(place.placeName()).contains(storeName));
		}

		@ParameterizedTest
		@MethodSource("providePointInvalidPair")
		@DisplayName("x, y 좌표 중 하나만 입력하면 예외를 발생시킨다")
		void search_place_failed_with_x_or_y(Double x, Double y) {
			// given
			String storeName = "한국계";
			PlaceSearchRequest searchRequest = new PlaceSearchRequest(storeName, x, y);

			// then
			assertThatThrownBy(() -> kakaoPlaceApiService.searchPlaces(searchRequest))
				.isInstanceOf(InvalidXYException.class);
		}

		static Stream<Arguments> providePointInvalidPair() {
			return Stream.of(
				Arguments.of(127.10796497353, null),
				Arguments.of(null, 37.5145458257413)
			);
		}
	}

	@Nested
	@DisplayName("주소를 검색할 때")
	class SearchAddressTest {

		@Test
		@DisplayName("입력값이 정상이면 장소 목록이 조회된다")
		void search_address_success() {
		    // given
			String query = "서울 송파구 잠실동 177-5";
			AddressSearchRequest request = new AddressSearchRequest(query);

			// when
			AddressSearchResponse responses = kakaoPlaceApiService.searchAddress(request);

			// then
			assertThat(responses.getAddressResponses()).isNotEmpty()
				.allSatisfy(address ->
					assertThat(address).isNotNull().satisfiesAnyOf(
						addr -> assertThat(addr.addressName()).contains(query),
						addr -> assertThat(addr.roadAddressName()).contains(query)
					)
				);
		}
	}
}
