package com.jjikmuk.sikdorak.acceptance.store;

import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_VERIFY_AND_SAVE_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_VERIFY_AND_SAVE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.mock.WireMockPlaceApiTest;
import com.jjikmuk.sikdorak.store.controller.request.StoreVerifyAndSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@WireMockPlaceApiTest
@DisplayName("StoreVerifyAndSave 인수테스트")
class StoreVerifyAndSaveAcceptanceTest extends InitAcceptanceTest {

	@Test
	@DisplayName("가게 등록 검증 요청을 하면 등록이 검증된 가게의 아이디를 반환한다")
	void search_store_by_name_containing_success() {
		ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_VERIFY_AND_SAVE_RESPONSE;
		StoreVerifyAndSaveRequest verifyAndSaveRequest = new StoreVerifyAndSaveRequest(1455921244L,
			"한국계", 127.111, 37.111);

		given(this.spec)
			.filter(
				document(
					DEFAULT_RESTDOC_PATH,
					STORE_VERIFY_AND_SAVE_REQUEST_SNIPPET,
					STORE_VERIFY_AND_SAVE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(verifyAndSaveRequest)

			.when().log().all()
			.put("/api/stores")

			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", equalTo(expectedCodeAndMessage.getCode()))
			.body("message", equalTo(expectedCodeAndMessage.getMessage()));
	}
}
