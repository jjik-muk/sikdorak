package com.jjikmuk.sikdorak.integration.user.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.jjikmuk.sikdorak.common.mock.OAuthMocks;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.user.auth.domain.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.service.OAuthService;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRespository;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
    "oauth.kakao.service.token_url=http://localhost:${wiremock.server.port}",
    "oauth.kakao.service.api_url=http://localhost:${wiremock.server.port}"
})
@DisplayName("OAuth 로그인 통합테스트")
public class OAuthLoginIntegrationTest extends InitIntegrationTest {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRespository userRespository;

    @BeforeEach
    void setUp() throws IOException {
        OAuthMocks.setUpResponses();
    }

    @Test
    @DisplayName("이메일 정보를 가진 유저일 경우 이메일을 저장하고 로그인에 성공한다.")
    public void oauth_login_success_with_user_email() {

        WireMock.setScenarioState("Email Not Null", "Started");

        JwtTokenPair code = oAuthService.login("code");
        String userId = jwtProvider.decodeToken(code.getAccessToken());
        User user = userRespository.findById(Long.parseLong(userId)).orElseThrow();

        assertThat(code.getAccessToken()).isNotNull();
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isNotNull();

    }

    @Test
    @DisplayName("이메일 정보가 없는 유저일 경우 이메일은 빈칸으로 저장하고 로그인에 성공한다.")
    public void oauth_login_success_without_user_email() {

        WireMock.setScenarioState("Email Null", "Started");

        JwtTokenPair code = oAuthService.login("code");
        String userId = jwtProvider.decodeToken(code.getAccessToken());

        User user = userRespository.findById(Long.parseLong(userId)).orElseThrow();

        assertThat(code.getAccessToken()).isNotNull();
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEmpty();

    }
}
