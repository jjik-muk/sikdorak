package com.jjikmuk.sikdorak.integration.user.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.tool.mock.OAuthMocks;
import com.jjikmuk.sikdorak.user.auth.app.JwtProvider;
import com.jjikmuk.sikdorak.common.oauth.ClientRegistrationRepository;
import com.jjikmuk.sikdorak.common.oauth.OAuthClientRegistration;
import com.jjikmuk.sikdorak.user.auth.app.dto.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.app.OAuthService;
import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthAuthenticationRequest;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
    "oauth.registrations.kakao.token_url=http://localhost:${wiremock.server.port}/oauth/token",
    "oauth.registrations.kakao.user-info-url=http://localhost:${wiremock.server.port}/v2/user/me"
})
@DisplayName("통합 : OAuth 로그인")
class OAuthLoginIntegrationTest extends InitIntegrationTest {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRegistrationRepository registrationRepository;

    @BeforeEach
    void setUp() throws IOException {
        OAuthMocks.setUpResponses();
    }

    @Test
    @DisplayName("이메일 정보를 가진 유저일 경우 이메일을 저장하고 로그인에 성공한다.")
    void oauth_login_success_with_user_email() {
        WireMock.setScenarioState("Email Not Null", "Started");
        String registrationId = "kakao";
        String authorizationCode = "code";
        OAuthClientRegistration registration = registrationRepository.findRegistrationByName(
            registrationId);

        JwtTokenPair code = oAuthService.login(OAuthAuthenticationRequest.of(registration,authorizationCode));
        String userId = jwtProvider.decodeToken(code.getAccessToken());
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow();

        assertThat(code.getAccessToken()).isNotNull();
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isNotNull();
    }

    @Test
    @DisplayName("이메일 정보가 없는 유저일 경우 이메일은 빈칸으로 저장하고 로그인에 성공한다.")
    void oauth_login_success_without_user_email() {
        WireMock.setScenarioState("Email Null", "Started");
        String registrationId = "kakao";
        String authorizationCode = "code";
        OAuthClientRegistration registration = registrationRepository.findRegistrationByName(
            registrationId);

        JwtTokenPair code = oAuthService.login(OAuthAuthenticationRequest.of(registration,authorizationCode));
        String userId = jwtProvider.decodeToken(code.getAccessToken());

        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow();

        assertThat(code.getAccessToken()).isNotNull();
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEmpty();
    }
}
