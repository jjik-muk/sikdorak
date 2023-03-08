package com.jjikmuk.sikdorak.integration.user.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.api.LoginUserArgumentResolver;
import com.jjikmuk.sikdorak.user.user.command.domain.Authority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

@DisplayName("통합 : OAuth Argument Resolver")
class OAuthInterceptorIntegrationTest extends InitIntegrationTest {

	@Autowired
	private LoginUserArgumentResolver loginUserArgumentResolver;

	private MockHttpServletRequest mockHttpServletRequest;

	@BeforeEach
	void setUp() {
		mockHttpServletRequest = new MockHttpServletRequest();
	}

	@Test
	@DisplayName("올바른 유저 Id가 넘어오는 경우 로그인 유저 객체를 반환한다.")
	void oAuth_argument_resolver_success() {
		mockHttpServletRequest.addHeader("Authorization", testData.kukimValidAuthorizationHeader);

		LoginUser loginUser = (LoginUser) loginUserArgumentResolver.resolveArgument(null, null,
			new ServletWebRequest(mockHttpServletRequest), null);

		assertThat(loginUser.getId()).isEqualTo(testData.kukim.getId());
		assertThat(loginUser.getAuthority()).isEqualTo(Authority.USER);
	}

	@Test
	@DisplayName("토큰이 존재하지 않는 경우 Anonymous 로그인 유저 객체를 반환한다.")
	void oAuth_argument_resolver_fail() {
		LoginUser loginUser = (LoginUser) loginUserArgumentResolver.resolveArgument(null, null,
			new ServletWebRequest(mockHttpServletRequest), null);

		assertThat(loginUser.getAuthority()).isEqualTo(Authority.ANONYMOUS);
	}
}
