package com.jjikmuk.sikdorak.acceptance.user.user;


import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_SEARCH_REVIEWS_REQUEST_PARAM_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_SEARCH_REVIEWS_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * - [x] 비회원/친구관계 아닌 유저가 특정 유저의 리뷰 조회 (public 글만 조회)
 * - [x] 친구 관계인 회원이 리뷰 조회 (public / protected 글만 조회)
 * - [x] 본인의 글을 조회한 경우(public/protected/private 모두 조회)
 */

@DisplayName("특정 유저의 리뷰 전체 조회 인수 테스트")
class UserReviewsSearchByUserIdAcceptanceTest extends InitAcceptanceTest {

	@Test
	@DisplayName("비회원/친구 관계 아닌 유저가 특정 유저의 리뷰를 조회한다면 public 리뷰들만 보여준다.")
	void guest_search_user_reviews_success() {
		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					USER_SEARCH_REVIEWS_REQUEST_PARAM_SNIPPET,
					USER_SEARCH_REVIEWS_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")

		.when()
			.get("/api/users/{userId}/reviews", testData.hoi.getId())

		.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(ResponseCodeAndMessages.USER_SEARCH_REVIEWS_SUCCESS.getCode()))
			.body("message", Matchers.equalTo(ResponseCodeAndMessages.USER_SEARCH_REVIEWS_SUCCESS.getMessage()))
			.body("data.size()", Matchers.equalTo(1));
	}

	@Test
	@DisplayName("친구관계인 유저가 특정 유저의 리뷰를 조회한다면 public|protected 리뷰들을 보여준다.")
	void connection_user_search_user_reviews_success() {
		given(this.spec)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.followSendUserValidAuthorizationHeader)

		.when()
			.get("/api/users/{userId}/reviews", testData.hoi.getId())

		.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(ResponseCodeAndMessages.USER_SEARCH_REVIEWS_SUCCESS.getCode()))
			.body("message", Matchers.equalTo(ResponseCodeAndMessages.USER_SEARCH_REVIEWS_SUCCESS.getMessage()))
			.body("data.size()", Matchers.equalTo(2));

	}

	@Test
	@DisplayName("유저 자신의 리뷰를 조회한다면 public|protected|private, 전체 리뷰를 보여준다.")
	void self_user_search_user_reviews_success() {
		given(this.spec)
			.filter(
				document(DEFAULT_RESTDOC_PATH,
					USER_SEARCH_REVIEWS_REQUEST_PARAM_SNIPPET,
					USER_SEARCH_REVIEWS_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.followAcceptUserValidAuthorizationHeader)

		.when()
			.get("/api/users/{userId}/reviews", testData.hoi.getId())

		.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(ResponseCodeAndMessages.USER_SEARCH_REVIEWS_SUCCESS.getCode()))
			.body("message", Matchers.equalTo(ResponseCodeAndMessages.USER_SEARCH_REVIEWS_SUCCESS.getMessage()))
			.body("data.size()", Matchers.equalTo(3));
	}

}
