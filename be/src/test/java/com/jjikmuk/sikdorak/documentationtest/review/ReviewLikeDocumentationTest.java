package com.jjikmuk.sikdorak.documentationtest.review;

import static com.jjikmuk.sikdorak.documentationtest.review.ReviewSnippet.REVIEW_LIKE_SUCCESS_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.review.ReviewSnippet.REVIEW_LIKE_SUCCESS_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("문서화 : Review 좋아요 기능")
class ReviewLikeDocumentationTest extends InitDocumentationTest {

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
            .put("/api/reviews/{reviewId}/unlike", testData.followAcceptUserProtectedReview.getId())

        .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.REVIEW_UNLIKE_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.REVIEW_UNLIKE_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("비회원의 좋아요 요청일 경우 실패 상태코드를 반환한다.")
    void anonymous_like() {
        given()

        .when()
            .put("/api/reviews/{reviewId}/like", testData.user1PublicReview.getId())

        .then()
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .body("code", equalTo(ExceptionCodeAndMessages.NEED_LOGIN.getCode()))
            .body("message", equalTo(ExceptionCodeAndMessages.NEED_LOGIN.getMessage()));

    }

}
