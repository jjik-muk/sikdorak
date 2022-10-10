package com.jjikmuk.sikdorak.documentationtest.review;


import static com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages.UNAUTHORIZED_USER;
import static com.jjikmuk.sikdorak.documentationtest.review.ReviewSnippet.REVIEW_MODIFY_REQUEST_PARAM_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.review.ReviewSnippet.REVIEW_MODIFY_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.review.ReviewSnippet.REVIEW_MODIFY_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import com.jjikmuk.sikdorak.review.command.app.request.ReviewModifyRequest;
import java.time.LocalDate;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * - [x] 유저, 본인 리뷰 수정 정상 요청 유저
 * - [x] 본인 리뷰 수정 비정상 요청(없는 스토어 아이디) -> throw
 */
@DisplayName("문서화 : Review 수정")
class ReviewModifyDocumentationTest extends InitDocumentationTest {

	@Test
	@DisplayName("유저가 본인 리뷰의 수정 요청이 정상적인 경우라면 리뷰 수정 후 정상 상태 코드를 반환한다")
	void modify_review_success() {
		ReviewModifyRequest reviewModifyRequest = new ReviewModifyRequest(
			"Modify Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					REVIEW_MODIFY_REQUEST_PARAM_SNIPPET,
					REVIEW_MODIFY_REQUEST_SNIPPET,
					REVIEW_MODIFY_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.kukimValidAuthorizationHeader)
			.body(reviewModifyRequest)

		.when()
			.put("/api/reviews/{reviewId}", testData.kukimPublicReview.getId())

		.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(ResponseCodeAndMessages.REVIEW_MODIFY_SUCCESS.getCode()))
			.body("message",
				Matchers.equalTo(ResponseCodeAndMessages.REVIEW_MODIFY_SUCCESS.getMessage()));
	}

	@Test
	@DisplayName("유저가 다른사람의 리뷰 수정 요청 경우라면 예외를 발생시킨다.")
	void modify_review_failed() {
		ReviewModifyRequest reviewModifyRequest = new ReviewModifyRequest(
			"Modify Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					REVIEW_MODIFY_REQUEST_PARAM_SNIPPET,
					REVIEW_MODIFY_REQUEST_SNIPPET,
					REVIEW_MODIFY_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.jayValidAuthorizationHeader)
			.body(reviewModifyRequest)

		.when()
			.put("/api/reviews/{reviewId}", testData.kukimPublicReview.getId())

		.then()
			.statusCode(HttpStatus.UNAUTHORIZED.value())
			.body("code", Matchers.equalTo(UNAUTHORIZED_USER.getCode()))
			.body("message", Matchers.equalTo(UNAUTHORIZED_USER.getMessage()));
	}

}
