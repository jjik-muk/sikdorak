package com.jjikmuk.sikdorak.acceptance.store;

import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_INSERT_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_INSERT_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.store.controller.request.StoreInsertRequest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("StoreInsert 인수 테스트")
public class StoreInsertAcceptanceTest extends InitAcceptanceTest {

	@Test
	@DisplayName("가게 생성 요청이 정상적인 경우라면, 리뷰 생성 후 정상 상태 코드를 반환한다.")
	void create_store_success() {

		StoreInsertRequest storeInsertRequest = new StoreInsertRequest(
			"새로 생긴 가게",
			"02-0000-0000",
			"서울시 어쩌구 00길 00",
			null,
			37.49082,
			127.033417
		);

		ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_INSERT_SUCCESS;

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH,
				STORE_INSERT_REQUEST_SNIPPET,
				STORE_INSERT_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", testData.userValidAuthorizationHeader)
			.contentType(ContentType.JSON)
			.body(storeInsertRequest)

		.when()
			.post("/api/stores")

		.then()
			.statusCode(HttpStatus.CREATED)
			.body("code", Matchers.equalTo(expectedCodeAndMessage.getCode()))
			.body("message", Matchers.equalTo(expectedCodeAndMessage.getMessage()));
	}
}
