package com.jjikmuk.sikdorak.documentationtest.user.auth;

import static com.jjikmuk.sikdorak.documentationtest.user.auth.OAuthSnippet.LOGIN_SUCCESS_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.user.auth.OAuthSnippet.LOGIN_SUCCESS_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.mock.OAuthMocks;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
    "oauth.kakao.service.token_url=http://localhost:${wiremock.server.port}",
    "oauth.kakao.service.api_url=http://localhost:${wiremock.server.port}"
})
@DisplayName("OAuth 로그인 인수테스트")
class OAuthLoginDocumentationTest extends InitDocumentationTest {

    @BeforeAll
    static void setWireMockResponse() throws IOException {
        OAuthMocks.setUpResponses();
    }

    @Test
    @DisplayName("유저가 로그인 버튼을 눌렀을 경우 카카오 로그인 페이지 url을 반환해야 한다.")
    void redirect_to_social_login_page() {
        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH))

        .when()
            .redirects().follow(false)
            .get("/api/oauth/login")

        .then()
            .statusCode(HttpStatus.MOVED_PERMANENTLY.value())
            .header("Location", startsWith("https://kauth.kakao.com/oauth/authorize"));
    }

    @Test
    @DisplayName("유저로부터 로그인 요청이 들어오면 유저의 정보를 저장하고 토큰을 발급한다.")
    void user_login_success() {
        given(this.spec)
            .filter(document(DEFAULT_RESTDOC_PATH,
                LOGIN_SUCCESS_REQUEST_SNIPPET,
                LOGIN_SUCCESS_RESPONSE_SNIPPET))
            .queryParam("code", "code")
            .accept(MediaType.APPLICATION_JSON_VALUE)

        .when()
            .get("/api/oauth/callback")

        .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .cookie("refreshToken", notNullValue())
            .body("data.accessToken", notNullValue());
    }
}
