package com.jjikmuk.sikdorak.documentationtest.user.user;

import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_SEARCH_FOLLOWERS_FOLLOWINGS_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_SEARCH_FOLLOWERS_FOLLOWINGS_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("문서화 : User 팔로워 목록 조회")
class UserFollowersSearchByUserIdDocumentationTest extends InitDocumentationTest {

    @Test
    @DisplayName("회원의 특정 유저에 대한 팔로워 조회 요청이 올바를 경우 성공 상태코드를 반환한다.")
    void search_user_followers_success() {
        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                USER_SEARCH_FOLLOWERS_FOLLOWINGS_REQUEST_SNIPPET,
                USER_SEARCH_FOLLOWERS_FOLLOWINGS_RESPONSE_SNIPPET
            ))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", testData.kukimValidAuthorizationHeader)

        .when()
            .get("/api/users/{userId}/followers", testData.rumka.getId())

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.USER_SEARCH_FOLLOWERS_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.USER_SEARCH_FOLLOWERS_SUCCESS.getMessage()))
            .body("data.size()", equalTo(2));
    }

    @Test
    @DisplayName("비회원의 특정 유저에 대한 팔로워 조회 요청이 올바를 경우 성공 상태코드를 반환한다.")
    void search_user_followers_by_anonymous_success() {
        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                USER_SEARCH_FOLLOWERS_FOLLOWINGS_REQUEST_SNIPPET,
                USER_SEARCH_FOLLOWERS_FOLLOWINGS_RESPONSE_SNIPPET
            ))
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/users/{userId}/followers", testData.rumka.getId())

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.USER_SEARCH_FOLLOWERS_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.USER_SEARCH_FOLLOWERS_SUCCESS.getMessage()))
            .body("data.size()", equalTo(2));
    }

}
