package com.jjikmuk.sikdorak.acceptance.auth;

import static com.jjikmuk.sikdorak.acceptance.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_FAIL_RESPONSE_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_SUCCESS_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("OAuth 토큰 재발급 인수테스트")
public class OAuthUpdateTokenAcceptanceTest extends InitAcceptanceTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("유저로부터 액세스 토큰 갱신 요청이 올 경우 새로운 액세스 토큰을 발급한다")
    void update_access_token_success() {

        String refreshToken = jwtProvider.createRefreshToken(
            testData.user.getUniqueId().toString());

        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH,
                UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET,
                UPDATE_ACCESS_TOKEN_SUCCESS_RESPONSE_SNIPPET))
            .header("Cookie", "refreshToken=" + refreshToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/oauth/refresh")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("data.accessToken", notNullValue());
    }

    @Test
    @DisplayName("유저로부터 액세스 토큰 갱신 요청의 리프레쉬 토큰이 유효하지 않을 경우 실패 메세지를 전송한다.")
    void update_access_token_fail_expired_token() {

        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIzNjgyMjM2MzgiLCJleHAiOjE2MzA2MzkzNTF9.SnT_Nxgspg3cUomCieDyBRH9TowtWh21YIfAKntuguA";

        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH,
                UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET,
                UPDATE_ACCESS_TOKEN_FAIL_RESPONSE_SNIPPET))
            .header("Cookie", "refreshToken=" + invalidToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/oauth/refresh")

        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", equalTo(ExceptionCodeAndMessages.EXPIRED_TOKEN.getCode()))
            .body("message", equalTo(ExceptionCodeAndMessages.EXPIRED_TOKEN.getMessage()));
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
            .body("code", equalTo(ExceptionCodeAndMessages.USER_NOT_FOUND.getCode()))
            .body("message", equalTo(ExceptionCodeAndMessages.USER_NOT_FOUND.getMessage()));
    }

}
