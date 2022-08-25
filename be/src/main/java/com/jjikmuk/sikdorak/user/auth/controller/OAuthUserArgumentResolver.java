package com.jjikmuk.sikdorak.user.auth.controller;

import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import com.jjikmuk.sikdorak.user.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidTokenException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class OAuthUserArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_TYPE = "Bearer ";
	private final JwtProvider jwtProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthenticatedUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		String token;

		try {
			token = parseAuthorizationHeader(
				Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
			jwtProvider.validateToken(token);
		} catch (InvalidTokenException e) {
			return new LoginUser(Authority.ANONYMOUS);
		}

		Long userId = Long.valueOf(jwtProvider.decodeToken(token));
		return new LoginUser(userId, Authority.USER);
	}

	private String parseAuthorizationHeader(HttpServletRequest request) {
		String authorization = request.getHeader(AUTHORIZATION_HEADER);
		if (Objects.isNull(authorization) || authorization.isEmpty()) {
			throw new InvalidTokenException();
		}
		return authorization.replace(TOKEN_TYPE, "");
	}
}
