package com.jjikmuk.sikdorak.documentationtest.review;

import static com.jjikmuk.sikdorak.documentationtest.review.ReviewSnippet.REVIEW_RECOMMEND_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.review.ReviewSnippet.REVIEW_RECOMMEND_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.spec.internal.HttpStatus;

@DisplayName("문서화 : Review 피드조회(추천) 기능")
class ReviewsRecommendDocumentationTest extends InitDocumentationTest {

    @Test
    @DisplayName("유저의 요청이 올바른 경우 리뷰 피드 목록과 함께 성공 상태코드를 반환한다.")
    void review_recommend_success() {
        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                REVIEW_RECOMMEND_REQUEST_SNIPPET,
                REVIEW_RECOMMEND_RESPONSE_SNIPPET
            ))
            .header("Authorization", testData.user1ValidAuthorizationHeader)
            .queryParam("after", 0)
            .queryParam("size", 1)

        .when()
            .get("/api/reviews")

        .then()
            .statusCode(HttpStatus.OK)
            .body("code", equalTo(ResponseCodeAndMessages.REVIEWS_FEED_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.REVIEWS_FEED_SUCCESS.getMessage()))
            .body("data.reviews", hasSize(1));
    }

}


