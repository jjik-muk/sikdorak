package com.jjikmuk.sikdorak.acceptance.review;

import static com.jjikmuk.sikdorak.acceptance.review.ReviewSnippet.REVIEW_LIKE_SUCCESS_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.review.ReviewSnippet.REVIEW_LIKE_SUCCESS_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("리뷰 좋아요 인수 테스트")
public class ReviewLikeAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("유저의 좋아요 요청이 올바를 경우 성공 상태코드를 반환한다.")
    void review_like_success() {

        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                REVIEW_LIKE_SUCCESS_REQUEST_SNIPPET,
                REVIEW_LIKE_SUCCESS_RESPONSE_SNIPPET
            ))
            .header("Authorization", testData.user1ValidAuthorizationHeader)

        .when()
            .put("/api/reviews/{reviewId}/like", testData.user1PublicReview.getId())

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.REVIEW_LIKE_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.REVIEW_LIKE_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("유저의 좋아요 취소 요청이 올바를 경우 성공 상태코드를 반환한다.")
    void review_unlike_success() {
        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                REVIEW_LIKE_SUCCESS_REQUEST_SNIPPET,
                REVIEW_LIKE_SUCCESS_RESPONSE_SNIPPET
                ))
            .header("Authorization", testData.user1ValidAuthorizationHeader)

        .when()
            .put("/api/reviews/{reviewId}/unlike", testData.user1PublicReview.getId())

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.REVIEW_UNLIKE_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.REVIEW_UNLIKE_SUCCESS.getMessage()));
    }

}
