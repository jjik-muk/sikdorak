package com.jjikmuk.sikdorak.acceptance.auth;

import static com.jjikmuk.sikdorak.acceptance.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.auth.OAuthSnippet.UPDATE_ACCESS_TOKEN_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("OAuth 토큰 재발급 인수테스트")
public class OAuthUpdateTokenAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("유저로부터 액세스 토큰 갱신 요청이 올 경우 새로운 액세스 토큰을 발급한다")
    void update_access_token() {
        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH,
                UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET,
                UPDATE_ACCESS_TOKEN_RESPONSE_SNIPPET))
            .header("Cookie", "RefreshToken=refreshToken")
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/oauth/refresh")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("data.accessToken", notNullValue());
    }

}
