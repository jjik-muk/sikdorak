package com.jjikmuk.sikdorak.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("유저가 로그인 버튼을 눌렀을 경우 카카오 로그인 페이지 url을 반환해야 한다.")
    void redirect_to_social_login_page() {
        given()
                .when()
                .redirects().follow(false)
                .get("/api/oauth/login")
                .then()
                .statusCode(HttpStatus.MOVED_PERMANENTLY.value())
                .header("Location", "https://kauth.kakao.com/oauth/authorize");
    }

    @Test
    @DisplayName("유저로부터 로그인 요청이 들어오면 유저의 정보를 저장하고 토큰을 발급한다.")
    void new_user_login_success() {
        given()
                .queryParam("code", "code")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/oauth/callback")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", notNullValue())
                .body("accessToken", equalTo("testAccessToken"))
                .body("refreshToken", notNullValue())
                .body("accessToken", equalTo("testRefreshToken"));
    }
}
