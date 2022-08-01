package com.jjikmuk.sikdorak.auth.service.kakao;

import com.jjikmuk.sikdorak.auth.controller.dto.response.JwtTokenResponse;
import com.jjikmuk.sikdorak.auth.controller.dto.response.OAuthTokenResponse;
import com.jjikmuk.sikdorak.auth.controller.dto.response.UserInfoResponse;
import com.jjikmuk.sikdorak.auth.controller.dto.response.UserProfileResponse;
import com.jjikmuk.sikdorak.auth.service.OAuthService;
import com.jjikmuk.sikdorak.common.properties.KakaoProperties;
import com.jjikmuk.sikdorak.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOAuthService implements OAuthService {

    private static final String LOGIN_PAGE_URL = "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=%s";
    private final KakaoOAuthClient kakaoOAuthClient;
    private final KakaoApiClient kakaoApiClient;
    private final KakaoProperties kakaoProperties;


    public String getLoginPageUrl() {
        return String.format(LOGIN_PAGE_URL, kakaoProperties.getClientId(), kakaoProperties.getRedirectUri(), kakaoProperties.getResponseType());
    }

    @Transactional
    public JwtTokenResponse login(String code) {

        OAuthTokenResponse oAuthTokenResponse = getOAuthAccessToken(code);

        UserInfoResponse userInfo = getUserInformation(oAuthTokenResponse);
        UserProfileResponse userProfileResponse = userInfo.getProperties();
        User user = new User(userInfo.getId(), userProfileResponse.getNickname(), userProfileResponse.getProfileImage());

//        userService.save(user);
//        jwtProvider.createToken(user.getKakaoUniqueId());

        return null;
    }

    private OAuthTokenResponse getOAuthAccessToken(String code) {
        OAuthTokenResponse oAuthTokenResponse = kakaoOAuthClient.getAccessToken(
                kakaoProperties.getGrantType(),
                kakaoProperties.getClientId(),
                kakaoProperties.getRedirectUri(),
                code);
        return oAuthTokenResponse;
    }

    private UserInfoResponse getUserInformation(OAuthTokenResponse oAuthTokenResponse) {
        String authorizationHeader = String.format("%s %s", oAuthTokenResponse.getTokenType(), oAuthTokenResponse.getAccessToken());
        return kakaoApiClient.getUserInfo(authorizationHeader);
    }

}
