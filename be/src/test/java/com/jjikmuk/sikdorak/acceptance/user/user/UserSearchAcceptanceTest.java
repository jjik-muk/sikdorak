package com.jjikmuk.sikdorak.acceptance.user.user;

import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_SEARCH_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_SEARCH_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@DisplayName("유저 조회 인수테스트")
public class UserSearchAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("회원인 유저의 유저 조회 요청이 올바르다면 유저 정보를 반환한다.")
    void search_user_profile_success() {
        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                USER_SEARCH_REQUEST_SNIPPET,
                USER_SEARCH_RESPONSE_SNIPPET)
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", testData.user1ValidAuthorizationHeader)

        .when()
            .get("/api/users/{userId}", testData.user2.getId())

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("data.id", equalTo(testData.user2.getId().intValue()))
            .body("data.nickname", equalTo(testData.user2.getNickname()))
            .body("data.email", equalTo(testData.user2.getEmail()))
            .body("data.followStatus", equalTo(testData.user1.isFollowing(testData.user2)));
    }

    @Test
    @DisplayName("비회원인 유저의 유저 조회 요청이 올바르다면 유저 정보를 반환한다.")
    void anonymous_user_search_user_profile_success() {
        given(this.spec)
            .filter(document(
                    DEFAULT_RESTDOC_PATH,
                    USER_SEARCH_REQUEST_SNIPPET,
                    USER_SEARCH_RESPONSE_SNIPPET)
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/users/{userId}", testData.user2.getId())

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("data.id", equalTo(testData.user2.getId().intValue()))
            .body("data.nickname", equalTo(testData.user2.getNickname()))
            .body("data.email", equalTo(testData.user2.getEmail()));
    }

}
