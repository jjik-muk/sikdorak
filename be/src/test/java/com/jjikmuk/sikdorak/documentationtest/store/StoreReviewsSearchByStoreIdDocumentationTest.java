package com.jjikmuk.sikdorak.documentationtest.store;


import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_SEARCH_REVIEWS_PAGING_REQUEST_PARAM_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_SEARCH_REVIEWS_REQUEST_PARAM_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.store.StoreSnippet.STORE_SEARCH_REVIEWS_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("문서화 : Store(특정 가게)의 리뷰 리스트 조회")
class StoreReviewsSearchByStoreIdDocumentationTest extends InitDocumentationTest {

	@Test
	@DisplayName("로그인 여부에 상관없이 PUBLIC 리뷰들만 보여준다.")
	void search_store_public_reviews_success() {

		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					STORE_SEARCH_REVIEWS_REQUEST_PARAM_SNIPPET,
					STORE_SEARCH_REVIEWS_PAGING_REQUEST_PARAM_SNIPPET,
					STORE_SEARCH_REVIEWS_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.param("after", 0)
			.param("size", 1)

			.when()
			.get("/api/stores/{storeId}/reviews", testData.store.getId())

			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code",
				Matchers.equalTo(ResponseCodeAndMessages.STORE_SEARCH_REVIEW_SUCCESS.getCode()))
			.body("message",
				Matchers.equalTo(ResponseCodeAndMessages.STORE_SEARCH_REVIEW_SUCCESS.getMessage()));
	}
}
