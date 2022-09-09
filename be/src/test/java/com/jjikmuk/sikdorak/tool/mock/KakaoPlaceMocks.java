package com.jjikmuk.sikdorak.tool.mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.jjikmuk.sikdorak.tool.TestResourceUtils;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public abstract class KakaoPlaceMocks {

	private final static String PLACE_KEYWORD_SEARCH_URL = "/v2/local/search/keyword.json";

	private final static String PLACE_KEYWORD_SEARCH_RESPONSE_BODY = TestResourceUtils.getTestResourceWithPath("payload/get-kakao-search-place-response.json");

	private final static String ADDRESS_SEARCH_URL = "/v2/local/search/address.json";

	private final static String ADDRESS_SEARCH_RESPONSE_BODY = TestResourceUtils.getTestResourceWithPath("payload/get-kakao-search-address-response.json");

	private static StubMapping placeKeywordSearchStub;

	private static StubMapping addressSearchStub;

	public static void startAllMocks() {
		setupSearchPlacesStub();
		setupSearchAddressStub();
	}

	public static void resetAllMocks() {
		WireMock.removeStub(placeKeywordSearchStub);
		WireMock.removeStub(addressSearchStub);
	}

	public static void setupSearchPlacesStub() {
		placeKeywordSearchStub = stubFor(get(urlPathEqualTo(PLACE_KEYWORD_SEARCH_URL))
			.willReturn(aResponse()
				.withStatus(HttpStatus.OK.value())
				.withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
				.withBody(PLACE_KEYWORD_SEARCH_RESPONSE_BODY)
			)
		);
	}

	public static void setupSearchAddressStub() {
		addressSearchStub = stubFor(get(urlPathEqualTo(ADDRESS_SEARCH_URL))
			.willReturn(aResponse()
				.withStatus(HttpStatus.OK.value())
				.withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
				.withBody(ADDRESS_SEARCH_RESPONSE_BODY)
			)
		);
	}
}
