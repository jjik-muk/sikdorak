package com.jjikmuk.sikdorak.acceptance.auth;

import static com.jjikmuk.sikdorak.acceptance.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.auth.controller.response.JwtTokenResponse;
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


        JwtTokenResponse tokenResponse = jwtProvider.createTokenResponse(testData.user.getUniqueId().toString());

        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH,
                UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET,
                UPDATE_ACCESS_TOKEN_RESPONSE_SNIPPET))
            .header("Cookie", "RefreshToken=" + tokenResponse.getRefreshToken())
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/oauth/refresh")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("data.accessToken", notNullValue());
    }

    @Test
    @DisplayName("유저로부터 액세스 토큰 갱신 요청의 리프레쉬 토큰이 유효하지 않을 경우 실패 메세지를 전송한다.")
    void update_access_token_fail() {
        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH,
                UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET,
                UPDATE_ACCESS_TOKEN_RESPONSE_SNIPPET))
            .header("Cookie", "RefreshToken=InvalidToken")
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/oauth/refresh")

        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
//            .body("code", ExceptionCodeAndMessages.);
    }

}
