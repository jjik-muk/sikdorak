package com.jjikmuk.sikdorak.acceptance.review;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.spec.internal.HttpStatus;

/*
<1차 릴리즈>
    [] public 상태의 전체 피드를 제공하기
 */

/*
<2차 릴리즈>
    [] 비회원인 경우
        [] 추천하는 피드들을 우선으로 준다. (좋아요 많은 순? / 평점이 높은 순? / 위치 기반 가까운 순?(이건 너무 배민같긴한데) 등등 -> 설정할 수 있도록)
        [] public 상태의 리뷰만 제공한다.
    [] 회원인 경우
        [] 친구들의 피드들을 우선으로 준다. (최신순)
        [] public, protected 상태만 준다.
        [] 인기 피드들만 따로 볼 수 있다. (비회원의 추천 피드와 동일하게)
 */

@DisplayName("리뷰 피드 조회 인수 테스트")
class ReviewsFeedAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("유저의 요청이 올바른 경우 리뷰 피드 목록과 함께 성공 상태코드를 반환한다.")
    void review_feed_success() {
        given(this.spec)
            .header("Authorization", testData.user1ValidAuthorizationHeader)

        .when()
            .get("/api/reviews")

        .then()
            .statusCode(HttpStatus.OK)
            .body("code", equalTo(ResponseCodeAndMessages.REVIEWS_FEED_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.REVIEWS_FEED_SUCCESS.getMessage()));
    }

}


