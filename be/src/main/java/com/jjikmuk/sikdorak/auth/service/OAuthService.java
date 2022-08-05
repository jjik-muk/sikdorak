package com.jjikmuk.sikdorak.auth.service;

import com.jjikmuk.sikdorak.auth.controller.response.JwtTokenResponse;
import com.jjikmuk.sikdorak.auth.controller.response.KakaoAccountResponse;
import com.jjikmuk.sikdorak.auth.controller.response.OAuthTokenResponse;
import com.jjikmuk.sikdorak.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.common.properties.KakaoProperties;
import com.jjikmuk.sikdorak.user.domain.User;
import com.jjikmuk.sikdorak.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public JwtTokenResponse login(String code) {
        OAuthTokenResponse oAuthTokenResponse = getOAuthAccessToken(code);
        KakaoAccountResponse userInfo = getOAuthUserInformation(oAuthTokenResponse);

        if (!userService.isExistingUser(userInfo.getUniqueId())) {
            User user = new User(userInfo.getUniqueId(), userInfo.getNickname(), userInfo.getProfileImage(), userInfo.getEmail());
            userService.createUser(user);
            return jwtProvider.createTokenResponse(String.valueOf(user.getUniqueId()));
        }

        return jwtProvider.createTokenResponse(String.valueOf(userInfo.getUniqueId()));
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
