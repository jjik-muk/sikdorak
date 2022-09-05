package com.jjikmuk.sikdorak.acceptance.store;

import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_SEARCH_BY_RADIUS_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_SEARCH_BY_RADIUS_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("위치기반 스토어 목록 조회 문서테스트")
class StoreSearchByRadiusAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("위치기반 스토어 목록조회 요청이 올바른 경우 스토어 목록과 함께 성공 상태코드를 반환한다.")
    void stores_radius_success() {

        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                STORE_SEARCH_BY_RADIUS_REQUEST_SNIPPET,
                STORE_SEARCH_BY_RADIUS_RESPONSE_SNIPPET
            ))
            .queryParam("type", "maps")
            .queryParam("x", "127.123123")
            .queryParam("y", "37.123123")
            .queryParam("radius", "100")

        .when()
            .get("/api/stores")

        .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.STORE_SEARCH_BY_RADIUS_SUCCESS.getCode()))
            .body("message",
                equalTo(ResponseCodeAndMessages.STORE_SEARCH_BY_RADIUS_SUCCESS.getMessage()))
            .body("data.stores", hasSize(10));

    }

}