package com.jjikmuk.sikdorak.acceptance.user.user;


import static io.restassured.RestAssured.given;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * - [ ] 비회원/친구관계 아닌 유저가 특정 유저의 리뷰 조회 (public 글만 조회)
 * - [ ] 친구 관계인 회원이 리뷰 조회 (public / protected 글만 조회)
 * - [ ] 본인의 글을 조회한 경우(public/protected/private 모두 조회)
 */

@DisplayName("특정 유저의 리뷰 전체 조회 인수 테스트")
class UserReviewsSearchByUserIdAcceptanceTest extends InitAcceptanceTest {

	@Test
	@DisplayName("비회원/친구 관계 아닌 유저가 특정 유저의 리뷰를 조회한다면 public 리뷰들만 보여준다.")
	void guest_search_user_reviews_success() {
		given(this.spec)
			.log().all()
//			.filter(
//				document(DEFAULT_RESTDOC_PATH,
//					REVIEW_MODIFY_REQUEST_PARAM_SNIPPET,
//					REVIEW_MODIFY_REQUEST_SNIPPET,
//					REVIEW_MODIFY_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")

		.when()
			.get("/api/users/{userId}/reviews", testData.user1.getId())

		.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(ResponseCodeAndMessages.USER_REVIEWS_SEARCH_SUCCESS.getCode()))
			.body("message",
				Matchers.equalTo(ResponseCodeAndMessages.USER_REVIEWS_SEARCH_SUCCESS.getMessage()));
	}


}
