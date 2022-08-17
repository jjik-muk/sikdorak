package com.jjikmuk.sikdorak.acceptance.user.user;

import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_MODIFY_PROFILE_PATH_VARIABLE_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_MODIFY_PROFILE_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_MODIFY_PROFILE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.user.user.controller.request.UserProfileModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/*
 [] 유저 프로필 이미지 관련 테스트 추가 작성해야 함.
 */

public class UserAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("유저 프로필 업데이트 요청이 정상적인 경우라면 프로필을 업데이트하고 정상 상태코드를 반환한다.")
    void update_user_profile_success() {

        UserProfileModifyRequest userProfileModifyRequest = new UserProfileModifyRequest(
            "포키",
            "forkyy@gmail.com",
            "https://s3-asjkdah8d2.com"
        );

        given(this.spec)
            .filter(
                document(DEFAULT_RESTDOC_PATH,
                    USER_MODIFY_PROFILE_PATH_VARIABLE_SNIPPET,
                    USER_MODIFY_PROFILE_REQUEST_SNIPPET,
                    USER_MODIFY_PROFILE_RESPONSE_SNIPPET))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", "application/json")
            .header("Authorization", testData.userValidAuthorizationHeader)
            .body(userProfileModifyRequest)

        .when()
            .put("/api/user/{userId}", testData.user.getId())

        .then()
            .statusCode(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("유저 프로필 업데이트 요청이 정상적이지 않은 경우라면 예외 상태코드를 반환한다.")
    void update_user_profile_fail() {
        UserProfileModifyRequest userProfileModifyRequest = new UserProfileModifyRequest(
            "",
            "forkyy.gmail.com",
            "https://s3-asjkdah8d2.com"
        );

        given(this.spec)
            .filter(
                document(DEFAULT_RESTDOC_PATH,
                    USER_MODIFY_PROFILE_PATH_VARIABLE_SNIPPET,
                    USER_MODIFY_PROFILE_REQUEST_SNIPPET,
                    USER_MODIFY_PROFILE_RESPONSE_SNIPPET))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", "application/json")
            .header("Authorization", testData.userValidAuthorizationHeader)
            .body(userProfileModifyRequest)

        .when()
            .put("/api/user/{userId}", testData.user.getId())

        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
