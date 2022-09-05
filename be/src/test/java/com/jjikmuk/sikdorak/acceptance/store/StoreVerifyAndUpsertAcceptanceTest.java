package com.jjikmuk.sikdorak.acceptance.store;

import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_SEARCH_REQUEST;
import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_SEARCH_RESPONSE;
import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_VERIFY_AND_UPSERT_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_VERIFY_AND_UPSERT_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.store.controller.request.StoreVerifyAndUpsertRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("StoreVerifyAndUpsert 인수테스트")
public class StoreVerifyAndUpsertAcceptanceTest extends InitAcceptanceTest {

	@Test
	@DisplayName("가게 등록 검증 요청을 하면 등록이 검증된 가게의 아이디를 반환한다")
	void search_store_by_name_containing_success() {
		ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_VERIFY_AND_UPSERT_RESPONSE;
		StoreVerifyAndUpsertRequest upsertRequest = new StoreVerifyAndUpsertRequest(1L, "맛있는가게",
			123.111, 37.111);

		given(this.spec)
			.filter(
				document(
					DEFAULT_RESTDOC_PATH,
					STORE_VERIFY_AND_UPSERT_REQUEST_SNIPPET,
					STORE_VERIFY_AND_UPSERT_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(upsertRequest)

			.when()
			.put("/api/stores")

			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", equalTo(expectedCodeAndMessage.getCode()))
			.body("message", equalTo(expectedCodeAndMessage.getMessage()));
	}
}
