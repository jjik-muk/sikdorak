package com.jjikmuk.sikdorak.documentationtest.store;

import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_SEARCH_DETAIL_REQUEST_PARAM_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_SEARCH_DETAIL_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.CodeAndMessages;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import com.jjikmuk.sikdorak.store.domain.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("문서화 : 특정 Store 상세 조회")
public class StoreDetailDocumentationTest extends InitDocumentationTest {

    @Test
    @DisplayName("가게의 상세를 조회할때, 해당 가게가 존재하면 상세 정보가 반환된다.")
    void search_store_detail_success() {
        Store savedStore = testData.store;
        CodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_SEARCH_DETAIL_SUCCESS;

        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH,
                STORE_SEARCH_DETAIL_REQUEST_PARAM_SNIPPET,
                STORE_SEARCH_DETAIL_RESPONSE_SNIPPET))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", "application/json")
            .header("Authorization", testData.user1ValidAuthorizationHeader)

            .when()
            .get("/api/stores/{storeId}", savedStore.getId())

            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(expectedCodeAndMessage.getCode()))
            .body("message", equalTo(expectedCodeAndMessage.getMessage()))
            .body("data.storeId", equalTo(savedStore.getId().intValue()))
            .body("data.storeName", equalTo(savedStore.getStoreName()))
            .body("data.addressName", equalTo(savedStore.getAddressName()))
            .body("data.roadAddressName", equalTo(savedStore.getRoadAddressName()));
    }

    @Test
    @DisplayName("가게의 상세를 조회할때, 해당 가게가 존재하지 않으 상세 정보가 반환된다.")
    void search_store_detail_failed() {
        long notExistStoreId = Long.MIN_VALUE;
        CodeAndMessages expectedCodeAndMessage = ExceptionCodeAndMessages.NOT_FOUND_STORE;

        given()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", "application/json")
            .header("Authorization", testData.user1ValidAuthorizationHeader)

            .when()
            .get("/api/stores/{storeId}", notExistStoreId)

            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("code", equalTo(expectedCodeAndMessage.getCode()))
            .body("message", equalTo(expectedCodeAndMessage.getMessage()));
    }
}
