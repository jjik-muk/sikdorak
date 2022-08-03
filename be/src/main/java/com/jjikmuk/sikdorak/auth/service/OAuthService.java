package com.jjikmuk.sikdorak.auth.service;

import com.jjikmuk.sikdorak.auth.controller.dto.response.JwtTokenResponse;
import com.jjikmuk.sikdorak.auth.controller.dto.response.KakaoAccountResponse;
import com.jjikmuk.sikdorak.auth.controller.dto.response.OAuthTokenResponse;
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

    private static final String LOGIN_PAGE_URL = "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=%s";
    private final OAuthTokenClient OAuthTokenClient;
    private final OAuthApiClient OAuthApiClient;
    private final KakaoProperties kakaoProperties;
    private final UserService userService;
    private final JwtProvider jwtProvider;


    public String getLoginPageUrl() {
        return String.format(LOGIN_PAGE_URL, kakaoProperties.getClientId(), kakaoProperties.getRedirectUri(), kakaoProperties.getResponseType());
    }

    @Transactional
    public JwtTokenResponse login(String code) {

        OAuthTokenResponse oAuthTokenResponse = getOAuthAccessToken(code);
        KakaoAccountResponse userInfo = getOAuthUserInformation(oAuthTokenResponse);
        User user = new User(userInfo.getId(), userInfo.getNickname(), userInfo.getProfileImage());
        userService.createUser(user);

        return jwtProvider.createTokenResponse(String.valueOf(user.getUniqueId()));
    }

    private OAuthTokenResponse getOAuthAccessToken(String code) {
        return OAuthTokenClient.getAccessToken(
                kakaoProperties.getGrantType(),
                kakaoProperties.getClientId(),
                kakaoProperties.getRedirectUri(),
                code);
    }

    private KakaoAccountResponse getOAuthUserInformation(OAuthTokenResponse oAuthTokenResponse) {
        String authorizationHeader = String.format("%s %s", oAuthTokenResponse.getTokenType(), oAuthTokenResponse.getAccessToken());
        return OAuthApiClient.getUserInfo(authorizationHeader);
    }

}
