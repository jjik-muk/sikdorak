package com.jjikmuk.sikdorak.integration.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.auth.exception.InvalidTokenException;
import com.jjikmuk.sikdorak.auth.interceptor.OAuthUserInterceptor;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

@DisplayName("OAuth 인터셉터 통합 테스트")
public class OAuthUserInterceptorIntegrationTest extends InitIntegrationTest {

    private MockHttpServletRequest httpServletRequest;

    @Autowired
    private OAuthUserInterceptor oAuthUserInterceptor;

    @BeforeEach
    void setUp() {
        this.httpServletRequest = new MockHttpServletRequest();
    }

    @Test
    @DisplayName("정상적인 토큰이 들어오면 true를 반환하고 request에 유저의 Id를 추가한다.")
    void oAuth_interceptor_success() throws Exception {
        httpServletRequest.addHeader("Authorization",  testData.validAuthorizationHeader);

        boolean result = oAuthUserInterceptor.preHandle(httpServletRequest, null, null);

        assertThat(result).isTrue();
        assertThat(httpServletRequest.getAttribute("userId")).isEqualTo(testData.user.getId());
    }

    @Test
    @DisplayName("유효하지 않은 토큰이 들어오면 예외를 반환한다.")
    void oAuth_interceptor_fail() {
        httpServletRequest.addHeader("Authorization", "bearer " + testData.invalidAuthorizationHeader);

        assertThatThrownBy(() -> oAuthUserInterceptor.preHandle(httpServletRequest, null, null))
            .isInstanceOf(InvalidTokenException.class);
    }
}
