package com.jjikmuk.sikdorak.common.mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.jjikmuk.sikdorak.common.TestResourceUtils;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public abstract class KakaoPlaceMocks {

	private final static String PLACE_KEYWORD_SEARCH_URL = "/v2/local/search/keyword.json";

	private static final String SCENARIO_KEYWORD_SEARCH = "place_keyword_search_url";

	private final static String PLACE_RESPONSE_BODY = TestResourceUtils.getTestResourceWithPath("payload/get-kakao-search-place-response.json");

	private final static String ADDRESS_SEARCH_URL = "/v2/local/search/address.json";

	private static final String SCENARIO_ADDRESS_SEARCH = "address_search_url";

	private final static String ADDRESS_RESPONSE_BODY = TestResourceUtils.getTestResourceWithPath("payload/get-kakao-search-address-response.json");

	public static void startAllMocks() {
		startSearchPlacesMockScenario();
		startSearchAddressMockScenario();
	}

	public static void resetAllMocks() {
		WireMock.getAllScenarios()
			.forEach(scenario -> WireMock.resetScenario(scenario.getId()));
	}

	public static void startSearchPlacesMockScenario() {
		stubFor(get(urlPathEqualTo(PLACE_KEYWORD_SEARCH_URL))
			.inScenario(SCENARIO_KEYWORD_SEARCH)
			.whenScenarioStateIs(Scenario.STARTED)
			.willReturn(aResponse()
				.withStatus(HttpStatus.OK.value())
				.withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
				.withBody(PLACE_RESPONSE_BODY)
			)
		);

		WireMock.setScenarioState(SCENARIO_KEYWORD_SEARCH, Scenario.STARTED);
	}

	public static void resetSearchPlacesMockScenario() {
		WireMock.resetScenario(SCENARIO_KEYWORD_SEARCH);
	}

	public static void startSearchAddressMockScenario() {
		stubFor(get(urlPathEqualTo(ADDRESS_SEARCH_URL))
			.inScenario(SCENARIO_ADDRESS_SEARCH)
			.whenScenarioStateIs(Scenario.STARTED)
			.willReturn(aResponse()
				.withStatus(HttpStatus.OK.value())
				.withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
				.withBody(ADDRESS_RESPONSE_BODY)
			)
		);

		WireMock.setScenarioState(SCENARIO_ADDRESS_SEARCH, Scenario.STARTED);
	}

	public static void resetSearchAddressMockScenario() {
		WireMock.resetScenario(SCENARIO_ADDRESS_SEARCH);
	}
}
