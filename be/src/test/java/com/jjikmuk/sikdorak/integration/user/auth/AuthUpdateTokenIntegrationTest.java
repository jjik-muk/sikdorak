package com.jjikmuk.sikdorak.integration.user.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.app.AuthService;
import com.jjikmuk.sikdorak.user.auth.app.dto.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.exception.ExpiredTokenException;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : OAuth 토큰 갱신 기능")
class AuthUpdateTokenIntegrationTest extends InitIntegrationTest {

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("유효한 refresh token이 전달될 경우 새로운 access/refresh 토큰을 발급한다.")
    void update_access_refresh_token_success() {
        JwtTokenPair jwtTokenPair = authService.reassignAccessToken(testData.kukimRefreshToken);

        assertThat(jwtTokenPair.getAccessToken()).isNotNull();
        assertThat(jwtTokenPair.getRefreshToken()).isNotEqualTo(testData.kukimRefreshToken);
    }

    @Test
    @DisplayName("만료된 refresh token이 전달될 경우 예외를 반환한다.")
    void update_access_refresh_token_with_expired_token() {
        assertThatThrownBy(() -> authService.reassignAccessToken(testData.kukimExpiredRefreshToken))
            .isInstanceOf(ExpiredTokenException.class);
    }

    @Test
    @DisplayName("유효하지 않은 refresh token이 전달될 경우 예외를 반환한다.")
    void update_access_refresh_token_with_invalid_token() {
        assertThatThrownBy(() -> authService.reassignAccessToken(testData.kukimInvalidRefreshToken))
            .isInstanceOf(InvalidTokenException.class);
    }

}
