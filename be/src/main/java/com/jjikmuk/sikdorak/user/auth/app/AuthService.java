package com.jjikmuk.sikdorak.user.auth.app;

import com.jjikmuk.sikdorak.user.auth.app.dto.JwtTokenPair;
import com.jjikmuk.sikdorak.user.user.command.app.UserService;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

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

    //TODO: RefreshToken을 레디스에 저장하는 로직 만들기

    //TODO: 로그아웃 기능 만들기
}
