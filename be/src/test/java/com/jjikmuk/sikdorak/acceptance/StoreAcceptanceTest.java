package com.jjikmuk.sikdorak.acceptance;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("Store 인수테스트")
public class StoreAcceptanceTest extends InitAcceptanceTest {

    private static final Snippet STORE_FIND_REQUEST = requestParameters(
            parameterWithName("storeName").description("가게 이름 검색 키워드")
    );

    private static final Snippet STORE_FIND_RESPONSE = commonResponseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("가게 아이디"),
            fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
            fieldWithPath("contactNumber").type(JsonFieldType.STRING).description("가게 연락처"),
            fieldWithPath("baseAddress").type(JsonFieldType.STRING).description("주소"),
            fieldWithPath("detailAddress").type(JsonFieldType.STRING).description("상세 주소").optional(),
            fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
            fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도")
    );

    @Test
    @DisplayName("가게를 이름으로 검색할때 조건에 맞는 가게가 있으면 가게 목록이 반환된다.")
    void find_store_by_name_containing_success() {
        ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_FIND_SUCCESS;
        String storeNameSearchKeywork = "가게";

        given(this.spec)
                .filter(document(DEFAULT_RESTDOC_PATH, STORE_FIND_REQUEST, STORE_FIND_RESPONSE))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .param("storeName", storeNameSearchKeywork)

        .when()
                .get("/api/stores")

        .then().log().all()
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
