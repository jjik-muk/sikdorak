package com.jjikmuk.sikdorak.documentationtest.store;

import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_REMOVE_REQUEST_PARAM_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_REMOVE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.CodeAndMessages;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("문서화 : Store 삭제 기능")
class StoreRemoveDocumentationTest extends InitDocumentationTest {

	@Test
	@DisplayName("가게 삭제 요청이 정상적인 경우라면, 가게 삭제 후 정상 상태 코드를 반환한다.")
	void remove_store_success() {
		Long savedStoreId = testData.store.getId();

		ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.STORE_REMOVE_SUCCESS;

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH,
				STORE_REMOVE_REQUEST_PARAM_SNIPPET,
				STORE_REMOVE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", testData.user1ValidAuthorizationHeader)
			.contentType(ContentType.JSON)

		.when()
			.delete("/api/stores/{storeId}", savedStoreId)

		.then()
			.statusCode(HttpStatus.OK)
			.body("code", Matchers.equalTo(expectedCodeAndMessage.getCode()))
			.body("message", Matchers.equalTo(expectedCodeAndMessage.getMessage()));
	}

	@Test
	@DisplayName("가게 삭제 요청 시 존재하지 않는 가게에 대한 요청이라면, 삭제되지 않고 실패 메세지를 전송한다.")
	void remove_store_failed() {
		Long unsavedStoreId = Long.MIN_VALUE;

		CodeAndMessages expectedCodeAndMessage = ExceptionCodeAndMessages.NOT_FOUND_STORE;

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH,
				STORE_REMOVE_REQUEST_PARAM_SNIPPET,
				STORE_REMOVE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", testData.user1ValidAuthorizationHeader)
			.contentType(ContentType.JSON)

			.when()
			.delete("/api/stores/{storeId}", unsavedStoreId)

			.then()
			.statusCode(HttpStatus.NOT_FOUND)
			.body("code", Matchers.equalTo(expectedCodeAndMessage.getCode()))
			.body("message", Matchers.equalTo(expectedCodeAndMessage.getMessage()));
	}
}
