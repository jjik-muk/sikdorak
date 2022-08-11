package com.jjikmuk.sikdorak.user.auth.interceptor;

import com.jjikmuk.sikdorak.user.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidTokenException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class OAuthUserInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception{

        String token = parseAuthorizationHeader(request);
        jwtProvider.validateToken(token);
        String userId = jwtProvider.decodeToken(token);
        request.setAttribute("userId", Long.parseLong(userId));

        return true;
    }

    private String parseAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (Objects.isNull(authorization) || authorization.isEmpty()) {
            throw new InvalidTokenException();
        }
        return authorization.replace(TOKEN_TYPE, "");
    }
}
