package com.jjikmuk.sikdorak.acceptance.review;

import static io.restassured.RestAssured.given;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
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
	void modify_review_success() {
		given(this.spec)
			.log().all()
//			.filter(
//				document(DEFAULT_RESTDOC_PATH,
//					REVIEW_MODIFY_REQUEST_PARAM_SNIPPET,
//					REVIEW_MODIFY_REQUEST_SNIPPET,
//					REVIEW_MODIFY_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.user1ValidAuthorizationHeader)

		.when()
			.get("/api/reviews/{reviewId}", testData.user1PublicReview.getId())

		.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(ResponseCodeAndMessages.REVIEW_SEARCH_DETAIL_SUCCESS.getCode()))
			.body("message",
				Matchers.equalTo(ResponseCodeAndMessages.REVIEW_SEARCH_DETAIL_SUCCESS.getMessage()));
	}





}
