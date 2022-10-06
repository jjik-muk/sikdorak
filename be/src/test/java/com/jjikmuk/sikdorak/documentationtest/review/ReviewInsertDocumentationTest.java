package com.jjikmuk.sikdorak.documentationtest.review;

import static com.jjikmuk.sikdorak.documentationtest.review.ReviewSnippet.REVIEW_CREATE_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.review.ReviewSnippet.REVIEW_CREATE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import com.jjikmuk.sikdorak.review.command.app.request.ReviewCreateRequest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * TODO
 *  [ ] 이미지 업로드 기능 추가해야함
 *  [ ] 유저 추가해야함
 */
@DisplayName("문서화 : Review 생성")
class ReviewInsertDocumentationTest extends InitDocumentationTest {

	@Test
	@DisplayName("유저가 리뷰 생성 요청이 정상적인 경우라면 리뷰 생성 후 정상 상태 코드를 반환한다")
	void create_review_success() {
		ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(
			"Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					REVIEW_CREATE_REQUEST_SNIPPET,
					REVIEW_CREATE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.user1ValidAuthorizationHeader)
			.body(reviewCreateRequest)

		.when()
			.post("/api/reviews")

		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	@DisplayName("비회원이 리뷰 생성 요청한다면 예외를 반환한다")
	void create_review_failed() {
		ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(
			"Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					REVIEW_CREATE_REQUEST_SNIPPET,
					REVIEW_CREATE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(reviewCreateRequest)

			.when()
			.post("/api/reviews")

			.then()
			.statusCode(HttpStatus.UNAUTHORIZED.value());
	}

}
