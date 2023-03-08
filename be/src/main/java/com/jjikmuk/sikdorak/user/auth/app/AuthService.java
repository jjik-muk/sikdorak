package com.jjikmuk.sikdorak.user.auth.app;

import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.app.dto.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidTokenException;
import com.jjikmuk.sikdorak.user.user.command.app.UserService;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer ";
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public JwtTokenPair reassignAccessToken(String refreshToken) {

        jwtProvider.validateToken(refreshToken);
        String userId = jwtProvider.decodeToken(refreshToken);

        if (!userService.isExistingById(Long.parseLong(userId))) {
            throw new NotFoundUserException();
        }

        return jwtProvider.createTokenResponse(userId);
    }

    public LoginUser authenticate(HttpServletRequest request) {

        String token;
        try {
            token = parseAuthorizationHeader(request);
            jwtProvider.validateToken(token);
        }  catch (InvalidTokenException e) {
            return LoginUser.anonymous();
        }

        Long userId = Long.valueOf(jwtProvider.decodeToken(token));
        return LoginUser.user(userId);
    }

    private String parseAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (Objects.isNull(authorization) || authorization.isEmpty()) {
            throw new InvalidTokenException();
        }
        return authorization.replace(TOKEN_TYPE, "");
    }

    //TODO: RefreshToken을 레디스에 저장하는 로직 만들기

    //TODO: 로그아웃 기능 만들기
}
