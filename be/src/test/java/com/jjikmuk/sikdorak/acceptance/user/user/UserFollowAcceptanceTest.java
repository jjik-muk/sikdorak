package com.jjikmuk.sikdorak.acceptance.user.user;

import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_FOLLOW_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_FOLLOW_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.user.user.controller.request.UserFollowAndUnfollowRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("유저 팔로우 인수 테스트")
class UserFollowAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("유저의 팔로우 요청이 올바른 경우라면 성공 상태코드를 응답한다.")
    void user_follow_success() {

        UserFollowAndUnfollowRequest userFollowAndUnfollowRequest = new UserFollowAndUnfollowRequest(testData.user2.getId());

        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                USER_FOLLOW_REQUEST_SNIPPET,
                USER_FOLLOW_RESPONSE_SNIPPET
            ))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(ContentType.JSON)
            .header("Authorization", testData.user1ValidAuthorizationHeader)
            .body(userFollowAndUnfollowRequest)

        .when()
            .put("/api/user/follow")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.USER_FOLLOW_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.USER_FOLLOW_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("유저의 팔로우 취소 요청이 올바른 경우라면 성공 상태코드를 응답한다.")
    void user_unfollow_success() {

        UserFollowAndUnfollowRequest userFollowAndUnfollowRequest = new UserFollowAndUnfollowRequest(testData.user2.getId());

        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                USER_FOLLOW_REQUEST_SNIPPET,
                USER_FOLLOW_RESPONSE_SNIPPET
            ))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(ContentType.JSON)
            .header("Authorization", testData.user1ValidAuthorizationHeader)
            .body(userFollowAndUnfollowRequest)

        .when()
            .put("/api/user/unfollow")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.USER_UNFOLLOW_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.USER_UNFOLLOW_SUCCESS.getMessage()));

    }

}
