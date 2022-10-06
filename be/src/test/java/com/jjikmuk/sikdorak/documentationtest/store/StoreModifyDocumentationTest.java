package com.jjikmuk.sikdorak.documentationtest.store;

import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_MODIFY_REQUEST_PARAM_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_MODIFY_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_MODIFY_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.CodeAndMessages;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import com.jjikmuk.sikdorak.store.command.app.request.StoreModifyRequest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("문서화 : Store 수정 기능")
class StoreModifyDocumentationTest extends InitDocumentationTest {

	@Test
	@DisplayName("가게 수정 요청이 정상적인 경우라면, 가게 수정 후 정상 상태 코드를 반환한다.")
	void modify_store_success() {
		Long savedStoreId = testData.store.getId();

		StoreModifyRequest storeModifyRequest = new StoreModifyRequest(
			"업데이트된 가게 이름",
			"02-9999-9999",
			"서울시 어쩌구 00-00",
			"서울시 어쩌구 00길 00",
			127.033417,
			37.49082
		);

		String adminAuthHeader = testData.generator.validAuthorizationHeader(testData.admin);

		ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_MODIFY_SUCCESS;

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH,
				STORE_MODIFY_REQUEST_PARAM_SNIPPET,
				STORE_MODIFY_REQUEST_SNIPPET,
				STORE_MODIFY_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", adminAuthHeader)
			.contentType(ContentType.JSON)
			.body(storeModifyRequest)

		.when()
			.put("/api/stores/{storeId}", savedStoreId)

		.then()
			.statusCode(HttpStatus.OK)
			.body("code", Matchers.equalTo(expectedCodeAndMessage.getCode()))
			.body("message", Matchers.equalTo(expectedCodeAndMessage.getMessage()));
	}

	@Test
	@DisplayName("권한이 없는 사용자가 가게 수정 요청을 한다면, 예외를 발생시킨다.")
	void modify_store_failed() {
		Long savedStoreId = testData.store.getId();

		StoreModifyRequest storeModifyRequest = new StoreModifyRequest(
			"업데이트된 가게 이름",
			"02-9999-9999",
			"서울시 어쩌구 00-00",
			"서울시 어쩌구 00길 00",
			127.033417,
			37.49082
		);

		String notAdminAuthHeader = testData.generator.validAuthorizationHeader(testData.jay);

		CodeAndMessages expectedCodeAndMessage = ExceptionCodeAndMessages.UNAUTHORIZED_USER;

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH,
				STORE_MODIFY_REQUEST_PARAM_SNIPPET,
				STORE_MODIFY_REQUEST_SNIPPET,
				STORE_MODIFY_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", notAdminAuthHeader)
			.contentType(ContentType.JSON)
			.body(storeModifyRequest)

		.when()
			.put("/api/stores/{storeId}", savedStoreId)

		.then()
			.statusCode(HttpStatus.UNAUTHORIZED)
			.body("code", Matchers.equalTo(expectedCodeAndMessage.getCode()))
			.body("message", Matchers.equalTo(expectedCodeAndMessage.getMessage()));
	}
}
