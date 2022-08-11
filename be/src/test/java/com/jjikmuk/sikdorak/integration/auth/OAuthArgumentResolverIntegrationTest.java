package com.jjikmuk.sikdorak.integration.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.auth.controller.OAuthUserArgumentResolver;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.domain.User;
import com.jjikmuk.sikdorak.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

@DisplayName("OAuth Argument Resolver 통합 테스트")
class OAuthArgumentResolverIntegrationTest extends InitIntegrationTest {

    @Autowired
    private OAuthUserArgumentResolver oAuthUserArgumentResolver;

    private MockHttpServletRequest mockHttpServletRequest;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    @DisplayName("올바른 유저 Id가 넘어오는 경우 유저 객체를 반환한다.")
    void oAuth_argument_resolver_success() throws Exception {
        mockHttpServletRequest.setAttribute("userId", testData.user.getId());

        User user =(User) oAuthUserArgumentResolver.resolveArgument(null, null,
            new ServletWebRequest(mockHttpServletRequest), null);

        assertThat(user.getId()).isEqualTo(testData.user.getId());
        assertThat(user.getUniqueId()).isEqualTo(testData.user.getUniqueId());
        assertThat(user.getNickname()).isEqualTo(testData.user.getNickname());
        assertThat(user.getEmail()).isEqualTo(testData.user.getEmail());
    }

    @Test
    @DisplayName("유효하지 않은 유저 Id가 넘어오는 경우 예외를 반환한다.")
    void oAuth_argument_resolver_fail() {

        mockHttpServletRequest.setAttribute("userId", 999L);

        assertThatThrownBy(() -> oAuthUserArgumentResolver.resolveArgument(null, null,
            new ServletWebRequest(mockHttpServletRequest), null))
            .isInstanceOf(UserNotFoundException.class);
    }
}
