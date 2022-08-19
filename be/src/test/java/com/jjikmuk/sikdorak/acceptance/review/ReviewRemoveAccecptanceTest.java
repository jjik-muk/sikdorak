package com.jjikmuk.sikdorak.acceptance.review;

import static com.jjikmuk.sikdorak.acceptance.review.ReviewSnippet.REVIEW_REMOVE_REQUEST_PARAM_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.review.ReviewSnippet.REVIEW_REMOVE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * [x] Soft Delete
 * [x] 유저, 본인 리뷰 수정 정상 요청 유저
 * [x] 본인 리뷰 수정 비정상 요청(없는 스토어 아이디) -> throw
 */
@DisplayName("ReviewRemove 인수 테스트")
class ReviewRemoveAccecptanceTest extends InitAcceptanceTest {

	@Test
	@DisplayName("유저가 본인 리뷰의 삭제 요청이 정상적인 경우라면 리뷰 삭제 후 정상 상태 코드를 반환한다")
	void remove_review_success() {
		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					REVIEW_REMOVE_REQUEST_PARAM_SNIPPET,
					REVIEW_REMOVE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.user1ValidAuthorizationHeader)

		.when()
			.delete("/api/reviews/{reviewId}", testData.review.getId())

		.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(ResponseCodeAndMessages.REVIEW_REMOVE_SUCCESS.getCode()))
			.body("message",
				Matchers.equalTo(ResponseCodeAndMessages.REVIEW_REMOVE_SUCCESS.getMessage()));
	}

	@Test
	@DisplayName("비회원이 리뷰의 삭제 요청이 주어진다면 예외를 반환한다.")
	void remove_review_failed_needLogin() {
		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					REVIEW_REMOVE_REQUEST_PARAM_SNIPPET,
					REVIEW_REMOVE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")

		.when()
			.delete("/api/reviews/{reviewId}", testData.review.getId())

		.then()
			.statusCode(HttpStatus.UNAUTHORIZED.value())
			.body("code", Matchers.equalTo(ExceptionCodeAndMessages.NEED_LOGIN.getCode()))
			.body("message",
				Matchers.equalTo(ExceptionCodeAndMessages.NEED_LOGIN.getMessage()));
	}

}
