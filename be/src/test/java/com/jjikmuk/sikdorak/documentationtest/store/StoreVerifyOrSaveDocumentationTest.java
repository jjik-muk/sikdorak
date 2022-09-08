package com.jjikmuk.sikdorak.documentationtest.store;

import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_VERIFY_OR_SAVE_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_VERIFY_OR_SAVE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.mock.WireMockPlaceApiTest;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import com.jjikmuk.sikdorak.store.controller.request.StoreVerifyOrSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@WireMockPlaceApiTest
@DisplayName("문서화 : Store 검증 및 저장 (카카오 API 사용)")
class StoreVerifyOrSaveDocumentationTest extends InitDocumentationTest {

	@Test
	@DisplayName("가게 등록 검증 요청을 하면 등록이 검증된 가게의 아이디를 반환한다")
	void search_store_by_name_containing_success() {
		ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_VERIFY_OR_SAVE_RESPONSE;
		StoreVerifyOrSaveRequest verifyOrSaveRequest = new StoreVerifyOrSaveRequest(1455921244L,
			"한국계", 127.079996336912,  37.5107289013413);

		given(this.spec)
			.filter(
				document(
					DEFAULT_RESTDOC_PATH,
					STORE_VERIFY_OR_SAVE_REQUEST_SNIPPET,
					STORE_VERIFY_OR_SAVE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(verifyOrSaveRequest)

		.when()
			.put("/api/stores")

		.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", equalTo(expectedCodeAndMessage.getCode()))
			.body("message", equalTo(expectedCodeAndMessage.getMessage()));
	}
}
