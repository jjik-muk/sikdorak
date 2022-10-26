package com.jjikmuk.sikdorak.documentationtest.user.user;

import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_REVIEW_SEARCH_BY_RADIUS_REQUEST;
import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_REVIEW_SEARCH_BY_RADIUS_RESPONSE;
import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_SEARCH_REVIEWS_REQUEST_PATH_PARAM_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("문서화: 위치기반 유저의 리뷰목록 조회")
class UserReviewsSearchByRadiusDocumentationTest extends InitDocumentationTest {

    @Test
    @DisplayName("위치기반 유저 리뷰목록 조회 요청이 올바른 경우 리뷰목록과 함께 성공 상태코드를 반환한다.")
    void reviews_radius_success() {
        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                USER_SEARCH_REVIEWS_REQUEST_PATH_PARAM_SNIPPET,
                USER_REVIEW_SEARCH_BY_RADIUS_REQUEST,
                USER_REVIEW_SEARCH_BY_RADIUS_RESPONSE
            ))
            .queryParam("type", "maps")
            .queryParam("x", "127.067")
            .queryParam("y", "37.6557")
            .queryParam("radius", "1000")
            .queryParam("after", "0")
            .queryParam("size", "10")

        .when()
            .get("/api/users/{userId}/reviews", 1)

        .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .body("code",
                equalTo(ResponseCodeAndMessages.USER_REVIEW_SEARCH_BY_RADIUS_SUCCESS.getCode()))
            .body("message",
                equalTo(ResponseCodeAndMessages.USER_REVIEW_SEARCH_BY_RADIUS_SUCCESS.getMessage()));

    }

}
