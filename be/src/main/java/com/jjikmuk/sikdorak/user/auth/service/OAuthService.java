package com.jjikmuk.sikdorak.user.auth.service;

import com.jjikmuk.sikdorak.common.properties.KakaoProperties;
import com.jjikmuk.sikdorak.user.auth.controller.response.AccessTokenResponse;
import com.jjikmuk.sikdorak.user.auth.controller.response.KakaoAccountResponse;
import com.jjikmuk.sikdorak.user.auth.controller.response.OAuthTokenResponse;
import com.jjikmuk.sikdorak.user.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.user.auth.domain.JwtTokenPair;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService{
    private final OAuthTokenClient oAuthTokenClient;
    private final OAuthApiClient oAuthApiClient;
    private final KakaoProperties kakaoProperties;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public String getLoginPageUrl() {
        return kakaoProperties.getLoginPageUrl();
    }

    @Transactional
    public JwtTokenPair login(String code) {
        OAuthTokenResponse oAuthTokenResponse = getOAuthAccessToken(code);
        KakaoAccountResponse userInfo = getOAuthUserInformation(oAuthTokenResponse);

        User user;
        if (!userService.isExistingByUniqueId(userInfo.getUniqueId())) {
            user = new User(userInfo.getUniqueId(), userInfo.getNickname(), userInfo.getProfileImage(), userInfo.getEmail());
            userService.createUser(user);
            return jwtProvider.createTokenResponse(String.valueOf(user.getId()));
        }
        user = userService.searchByUniqueId(userInfo.getUniqueId());
        return jwtProvider.createTokenResponse(String.valueOf(user.getId()));
    }

    @Transactional(readOnly = true)
    public AccessTokenResponse updateAccessToken(String refreshToken) {

        jwtProvider.validateToken(refreshToken);
        String userId = jwtProvider.decodeToken(refreshToken);
        if (!userService.isExistingById(Long.parseLong(userId))) {
            throw new NotFoundUserException();
        }
        return new AccessTokenResponse(jwtProvider.createAccessToken(userId));
    }

    private OAuthTokenResponse getOAuthAccessToken(String code) {
        return oAuthTokenClient.getAccessToken(
                kakaoProperties.getGrantType(),
                kakaoProperties.getClientId(),
                kakaoProperties.getRedirectUri(),
                code);
    }

    private KakaoAccountResponse getOAuthUserInformation(OAuthTokenResponse oAuthTokenResponse) {
        String authorizationHeader = String.format("%s %s", oAuthTokenResponse.getTokenType(), oAuthTokenResponse.getAccessToken());
        return oAuthApiClient.getUserInfo(authorizationHeader);
    }
}
