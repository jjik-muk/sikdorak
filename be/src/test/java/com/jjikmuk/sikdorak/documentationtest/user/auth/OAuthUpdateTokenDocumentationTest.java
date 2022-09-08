package com.jjikmuk.sikdorak.documentationtest.user.auth;

import static com.jjikmuk.sikdorak.documentationtest.user.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_FAIL_RESPONSE_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.user.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.user.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_SUCCESS_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("OAuth 토큰 재발급 인수테스트")
class OAuthUpdateTokenDocumentationTest extends InitDocumentationTest {

    @Test
    @DisplayName("유저로부터 액세스 토큰 갱신 요청이 올 경우 새로운 액세스 토큰을 발급한다")
    void update_access_token_success() {

        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH,
                UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET,
                UPDATE_ACCESS_TOKEN_SUCCESS_RESPONSE_SNIPPET))
            .header("Cookie", "refreshToken=" + testData.user1RefreshToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/oauth/refresh")

        .then()
            .statusCode(HttpStatus.OK.value())
            .cookie("refreshToken", not(equalTo(testData.user1RefreshToken)))
            .body("data.accessToken", notNullValue());
    }

    @Test
    @DisplayName("유저로부터 액세스 토큰 갱신 요청의 리프레쉬 토큰에 담긴 유저의 id가 유효하지 않을 경우 실패 메세지를 전송한다.")
    void update_access_token_fail_user_not_found() {

        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIzNjgyMjM2MzIiLCJleHAiOjE3MjA2MzkzNTF9.PB7YOSEJqmwnrHddE0ZbGXlUWlK_NG0hmQjBv8d_9cE";

        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH,
                UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET,
                UPDATE_ACCESS_TOKEN_FAIL_RESPONSE_SNIPPET))
            .header("Cookie", "refreshToken=" + invalidToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/oauth/refresh")

        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("code", equalTo(ExceptionCodeAndMessages.NOT_FOUND_USER.getCode()))
            .body("message", equalTo(ExceptionCodeAndMessages.NOT_FOUND_USER.getMessage()));
    }

}
