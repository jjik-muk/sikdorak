package com.jjikmuk.sikdorak.acceptance.store;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.store.domain.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_FIND_REQUEST;
import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_FIND_RESPONSE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("Store 인수테스트")
public class StoreAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("가게를 이름으로 검색할때 조건에 맞는 가게가 있으면 가게 목록이 반환된다.")
    void find_store_by_name_containing_success() {
        Store savedStore = testData.store;

        ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_FIND_SUCCESS;
        String storeNameSearchKeywork = savedStore.getStoreName();

        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH, STORE_FIND_REQUEST, STORE_FIND_RESPONSE))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", "application/json")
            .header("Authorization", testData.validAuthorizationHeader)
            .param("storeName", storeNameSearchKeywork)

        .when()
            .get("/api/stores")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(expectedCodeAndMessage.getCode()))
            .body("message", equalTo(expectedCodeAndMessage.getMessage()))
            .body("data.id", hasItem(savedStore.getId().intValue()))
            .body("data.storeName", everyItem(containsString(savedStore.getStoreName())));
    }

    @Test
    @DisplayName("가게를 이름으로 검색할때 조건에 맞는 가게가 없으면 빈 목록이 반환된다.")
    void find_store_by_name_containing_failed() {
        ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_FIND_SUCCESS;
        String notExistStoreName = "존재하지않는가게이름테스트를위한가게이름";

        given()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", "application/json")
            .header("Authorization", testData.validAuthorizationHeader)
            .param("storeName", notExistStoreName)

        .when()
            .get("/api/stores")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(expectedCodeAndMessage.getCode()))
            .body("message", equalTo(expectedCodeAndMessage.getMessage()))
            .body("data", empty());
    }
}