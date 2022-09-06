package com.jjikmuk.sikdorak.common.mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public abstract class KakaoPlaceMocks {

	private final static String PLACE_KEYWORD_SEARCH_URL = "/v2/local/search/keyword.json";

	private static final String SCENARIO_KEYWORD_SEARCH = "place_keyword_search_url";


	public static void startSearchPlacesMockScenario() {
		stubFor(get(urlPathEqualTo(PLACE_KEYWORD_SEARCH_URL))
			.inScenario(SCENARIO_KEYWORD_SEARCH)
			.whenScenarioStateIs(Scenario.STARTED)
			.willReturn(aResponse()
				.withStatus(HttpStatus.OK.value())
				.withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
				.withBody(Payload.PLACE_RESPONSE_BODY)
			)
		);

		WireMock.setScenarioState(SCENARIO_KEYWORD_SEARCH, Scenario.STARTED);
	}

	public static void resetSearchPlacesMockScenario() {
		stubFor(get(urlPathEqualTo(PLACE_KEYWORD_SEARCH_URL))
			.inScenario(SCENARIO_KEYWORD_SEARCH)
			.whenScenarioStateIs(Scenario.STARTED)
			.willReturn(aResponse()
				.withStatus(HttpStatus.OK.value())
				.withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
				.withBody(Payload.PLACE_RESPONSE_BODY)
			)
		);

		WireMock.resetScenario(SCENARIO_KEYWORD_SEARCH);
	}

	private static class Payload {
		private final static String PLACE_RESPONSE_BODY = """
{
    "documents": [
        {
            "address_name": "서울 송파구 잠실동 177-5",
            "category_group_code": "FD6",
            "category_group_name": "음식점",
            "category_name": "음식점 > 한식 > 육류,고기 > 닭요리",
            "distance": "",
            "id": "1455921244",
            "phone": "02-424-7077",
            "place_name": "한국계",
            "place_url": "http://place.map.kakao.com/1455921244",
            "road_address_name": "서울 송파구 올림픽로8길 10",
            "x": "127.079996336912",
            "y": "37.5107289013413"
        },
        {
            "address_name": "서울 송파구 방이동 24-4",
            "category_group_code": "FD6",
            "category_group_name": "음식점",
            "category_name": "음식점 > 한식 > 육류,고기",
            "distance": "",
            "id": "415215581",
            "phone": "02-420-7077",
            "place_name": "한국계 방이직영점",
            "place_url": "http://place.map.kakao.com/415215581",
            "road_address_name": "서울 송파구 오금로11길 7",
            "x": "127.10796497353",
            "y": "37.5145458257413"
        }
    ],
    "meta": {
        "is_end": true,
        "pageable_count": 2,
        "same_name": {
            "keyword": "한국계",
            "region": [],
            "selected_region": ""
        },
        "total_count": 2
    }
}""";
	}
}
