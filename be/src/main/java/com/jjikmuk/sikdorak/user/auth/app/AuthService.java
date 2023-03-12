package com.jjikmuk.sikdorak.user.auth.app;

import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.app.dto.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidTokenException;
import com.jjikmuk.sikdorak.user.user.command.app.UserService;
import com.jjikmuk.sikdorak.user.user.command.domain.Authority;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.util.Map;
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
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public JwtTokenPair reassignAccessToken(String refreshToken) {

        jwtProvider.validateToken(refreshToken);
        Map<String, String> payloads = jwtProvider.decodeToken(refreshToken);
        User user = userRepository.findById(Long.parseLong(payloads.get("id")))
            .orElseThrow(NotFoundUserException::new);

        return jwtProvider.createTokenResponse(user);
    }

    public LoginUser authenticate(HttpServletRequest request) {

        try {
            String token = parseAuthorizationHeader(request);
            jwtProvider.validateToken(token);
            Map<String, String> payloads = jwtProvider.decodeToken(token);

            Long id = Long.parseLong(payloads.get("id"));
            String authority = payloads.get("authority");

            if (Authority.ADMIN.name().equals(authority)) {
                return LoginUser.admin(id);
            }
            return LoginUser.user(id);
        }  catch (InvalidTokenException e) {
            return LoginUser.anonymous();
        }
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
