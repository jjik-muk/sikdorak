package com.jjikmuk.sikdorak.acceptance.review;

import static com.jjikmuk.sikdorak.acceptance.review.ReviewSnippet.REVIEW_DETAIL_SEARCH_REQUEST_PARAM_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.review.ReviewSnippet.REVIEW_DETAIL_SEARCH_RESPONSE_FAILED_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.review.ReviewSnippet.REVIEW_DETAIL_SEARCH_RESPONSE_SUCESS_SNIPPET;
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
 * 디테일 조회 가능한 사람이 조회한 경우 : public 글
 * 조회 불가능한 사람이 조회한 경우 : 게스트/친구관계가 protected, private 조회 , 친구관계가 private 조회
 */
@DisplayName("리뷰조회 인수테스트")
class ReviewSearchAcceptanceTest extends InitAcceptanceTest {

	@Test
	@DisplayName("유저가 본인 리뷰를 조회한 경우 디테일 리뷰를 보여준다.")
	void search_review_detail_success() {
		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					REVIEW_DETAIL_SEARCH_REQUEST_PARAM_SNIPPET,
					REVIEW_DETAIL_SEARCH_RESPONSE_SUCESS_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.followAcceptUserValidAuthorizationHeader)

		.when()
			.get("/api/reviews/{reviewId}", testData.followAcceptUserPublicReview.getId())

		.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(ResponseCodeAndMessages.REVIEW_SEARCH_DETAIL_SUCCESS.getCode()))
			.body("message",
				Matchers.equalTo(ResponseCodeAndMessages.REVIEW_SEARCH_DETAIL_SUCCESS.getMessage()))
			.body("data.reviewVisibility", Matchers.equalTo("PUBLIC"));
	}


	@Test
	@DisplayName("게스트가 읽을 권한없는 리뷰를 조회한 경우 예외 문구를 보낸다.")
	void search_review_detail_failed() {
		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					REVIEW_DETAIL_SEARCH_REQUEST_PARAM_SNIPPET,
					REVIEW_DETAIL_SEARCH_RESPONSE_FAILED_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")

		.when()
			.get("/api/reviews/{reviewId}", testData.followAcceptUserPrivateReview.getId())

		.then()
			.statusCode(HttpStatus.UNAUTHORIZED.value())
			.body("code", Matchers.equalTo(ExceptionCodeAndMessages.UNAUTHORIZED_USER.getCode()))
			.body("message",
				Matchers.equalTo(ExceptionCodeAndMessages.UNAUTHORIZED_USER.getMessage()));
	}

}
